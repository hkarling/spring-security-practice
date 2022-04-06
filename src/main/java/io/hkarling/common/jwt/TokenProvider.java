package io.hkarling.common.jwt;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import io.hkarling.common.errors.CommonException;
import io.hkarling.common.errors.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;       // 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;

    public TokenProvider(@Value("${environment.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = new Date().getTime();

        Date accessTokenExpiry = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        // accessToken
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiry)
                .signWith(key, HS512)
                .compact();

        // refreshToken
        Date refreshTokenExpiry = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiry)
                .signWith(key, HS512)
                .compact();

        return new TokenResponse(BEARER_TYPE, accessToken, refreshToken, accessTokenExpiry.getTime(), refreshTokenExpiry.getTime());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseToken(accessToken);  // 복호화

        String strClaims = Optional.ofNullable(claims.get(AUTHORITIES_KEY))
                .orElseThrow(() -> new CommonException(ErrorCode.TOKEN_UNAUTHORIZED)).toString();
        List<? extends GrantedAuthority> authorities = Arrays.stream(strClaims.split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CommonException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CommonException(ErrorCode.TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException | SignatureException e) {
            throw new CommonException(ErrorCode.TOKEN_MALFORMED);
        } catch (IllegalArgumentException e) {
            throw new CommonException(ErrorCode.TOKEN_ILLEGAL);
        }
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
