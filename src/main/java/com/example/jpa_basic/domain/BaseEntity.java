package com.example.jpa_basic.domain;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;
/*
    여러 entity에서 반복되는 field가 있는 경우
    공통의 매핑 정보를 모음
    상속관계의 매핑이나 entity가 아니라 자식 클래스에 매핑할 정보만 제공
 */
@MappedSuperclass
public abstract class BaseEntity {
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
}
