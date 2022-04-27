package io.hkarling.common.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public class BaseEntity extends TimeBaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy = "SYSTEM";
    @LastModifiedBy
    private String lastModifiedBy = "SYSTEM";

}
