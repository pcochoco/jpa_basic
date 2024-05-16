package com.example.jpa_basic.domain;

import jakarta.persistence.*;

//OrderItem이 Item과 Order간 다대다 연관관계에서 각각 일대다, 다대일로 풀 수 있도록 하는 역할
@Entity
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;
}
