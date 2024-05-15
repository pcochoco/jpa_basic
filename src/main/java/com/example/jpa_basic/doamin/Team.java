package com.example.jpa_basic.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
//한 team에는 여러 member : 1 대 다
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)//연관관계의 주인이 아님
    /*영속성 전이 : 특정 엔티티를 영속 상태로 만들 때 연관 엔티티도 함께 영속 상태로 (매핑과 관련 x)
    고아 객체 : 부모와 연관관계가 끊어진 자식 엔티티
    => 두 개념은 특정 엔티티가 해당이 엔티를 소유하는 경우 사용
    다른 엔티티에서 예상치 못한 추가, 삭제 방지
    부모를 통한 자식의 생명주기 관리 -> 도메인 주도 설계의 Aggregate Root 개념 구현에 유용
     */
    List<Member> members = new ArrayList<>();
    public void addMember(Member member){
        this.members.add(member);
        member.setTeam(this);
    }

}
