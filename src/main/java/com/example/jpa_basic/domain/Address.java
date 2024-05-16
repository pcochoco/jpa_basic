package com.example.jpa_basic.domain;

import jakarta.persistence.Embeddable;

/*
    Embedded type : 기본 값을 모아 새 값 타입을 정의

    불변 객체 : setter x or private setter로 구현
    생성 시점 이후 절대 값 변경 안하는 객체
 */
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //Embedded으로 사용할 클래스는 기본 생성자 필수
    //엔티티의 값일 뿐이므로 타입을 사용해도 매핑 테이블에 변함 없음
    public Address(){

    }

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private void setCity(String city){
        this.city = city;
    }

    private void setStreet(String street){
        this.street = street;
    }

    private  void  setZipcode(String zipcode){
        this.zipcode = zipcode;
    }
}
