package com.example.jpa_basic.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "movie") //DTYPE에 들어갈 값 지정 : 부모로 자식 엔티티 구별용
public class Movie extends Item{
    private String director;
    private String actor;
}
