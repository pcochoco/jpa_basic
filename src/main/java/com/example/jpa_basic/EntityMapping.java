package com.example.jpa_basic;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import com.example.jpa_basic.doamin.Member;
import com.example.jpa_basic.doamin.Team;

import java.util.List;

//연관관계 메서드가 없을 때의 문제점 관련
public class EntityMapping {
    public static void entity_mapping() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member1 = new Member();
        member1.setName("memberA");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setName("memberB");
        member2.setTeam(team);
        em.persist(member2);

        Member member3 = new Member();
        member3.setName("memberC");
        /*team.getMembers().add(member3); //연관관계에서 역방향이 되는 경우 : team에서 저장함
            flush되기 전 team이 참조하는 member은 순수 객체상태 member3
            flush된 후 연관관계 주인은 Member이므로 member1과 member2만 team과 연관관계를 갖게 됨
            setTeam으로 단방향으로만 field을 주입하지 않고 양쪽 모두 값을 넣어줄 필요성이 생김
         */

        member3.addTeam(team);
        em.persist(member3);

        Team findTeam = em.find(Team.class, team.getId());


        //양방향 메서드 추가한 경우 member1 ~ 3까지 모두 list에 존재
        List<Member> findTeamMembers = findTeam.getMembers();
        for (Member m : findTeamMembers) {
            System.out.println("m = " + m.getId() + ": " + m.getName());
        }

        em.flush();
        em.clear();

        //flush되더라도 양쪽 모두 값 설정 됨
        List<Member> members = findMember.getTeam().getMembers();
        for (Member m : members) {
            System.out.println("m = " + m.getId() + ": " + m.getName());
        }
    }

}
