package io.hkarling.member.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
public class RefreshToken {

    @Id
    String key;
    @Setter
    String value;
    LocalDateTime expiryDate;

    public void updateValue(String token, LocalDateTime expiry) {
        this.value = token;
        this.expiryDate = expiry;
    }

}
