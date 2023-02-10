package project.toy.api.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass // MappedSuperclass 상속시 클래스의 필드값들도 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity {

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
