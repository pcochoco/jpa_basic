package com.example.jpa_basic.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    //한 team에는 여러 member : 1 대 다
    @OneToMany(mappedBy = "team")//연관관계의 주인이 아님
    List<Member> members = new ArrayList<>();



}
