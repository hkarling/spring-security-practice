package io.hkarling.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public class TimeBaseEntity {

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;

}
