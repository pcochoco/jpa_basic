package com.example.jpa_basic.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

//entity 구조
@Entity
@Getter @Setter
@Table(name = "MEMBER")
public class Member {
    @Id //id만 써서 직접 할당 가능
    @GeneratedValue(strategy = GenerationType.AUTO) //추가 자동생성 동작, DB dialect에 따른 지정
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING) //enum type mapping STRING > ORDINAL 권장
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //날짜
    private Date createdDate;

    private LocalDateTime lastModifiedDate;

    @Lob //CLOB(문자) + BLOB(나머지)
    private String description;

    @Transient
    private int temp;

    //연관관계 주인은 fk가 있는 쪽
    //수정 가능 / 아닌 쪽은 읽기만
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    //연관관계 메서드
    public void addTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    public static Member findMember(String name){
        for (Member member : team.getMembers()){ //
            if (member.getId() == id){
                return member;
            }
        }
    }
}
