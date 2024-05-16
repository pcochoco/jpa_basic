package com.example.jpa_basic.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Order과 OrderItem은 일대다 관계
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToMany(mappedBy = "order") //읽기용
    //Order과 Item의 관계는 N : M -> OrderItem으로 1 : N, M : 1로 풀어 설정
    //fk를 가지는 쪽이 OrderItem = 연관관계의 주인
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
