package project.toy.api.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass // MappedSuperclass 상속시 클래스의 필드값들도 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(updatable = false)
    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String lastModifiedAt;

    @PrePersist // 엔티티 insert 이전 실행
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.lastModifiedAt = this.createdAt;
    }

    @PreUpdate // 엔티티 update 이전 실행
    public void onPreUpdate(){
        this.lastModifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
