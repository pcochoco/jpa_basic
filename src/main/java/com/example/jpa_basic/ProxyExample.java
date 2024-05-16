package com.example.jpa_basic;

import com.example.jpa_basic.domain.Member;
import com.example.jpa_basic.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/*
    proxy 객체 = 실제 객체 참조 보관용 가짜 객체
    -> 영속성 컨텍스트를 통해 db에 조회
    -> 가져온 정보로 실제 엔티티 생성, 참조
    -> 프록시 객체 호출 시 실제 객체의 메서드 호출

    - 처음 한번 사용 시 초기화
    - 초기화 시 엔티티로 바뀌는 것이 아니라 참조 동작
    - 원본 엔티티 상속 : == 대신 instance of (다른 transaction에서)
    - 이미 찾고자 하는 엔티티가 있다면 실제 엔티티 반환
    - 준영속 상태인 경우 예외 처리
 */
public class ProxyExample {

    public static void Use_Proxy(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Team team = new Team();
        team.setName("A");

        Member member = new Member();
        member.setName("A");
        member.setTeam(team);

        em.persist(team);
        em.persist(member);

        em.flush(); //sql 보냄
        em.clear(); //persistence context 1차 캐시 초기화

        Member refMember = em.getReference(Member.class, member.getId()); //proxy 객체 가져옴
        System.out.println("ref Mem = " + refMember.getId() + ":" + refMember.getName());


        //(1) 실제 entity를 조회 후 프록시 객체 조회
        Member findMem = em.find(Member.class, member.getId()); //1차 cahce에 있는 엔티티 조회
        System.out.println("findMem.getClass()" + findMem.getClass());

        Member refMem = em.getReference(Member.class, member.getId()); //프록시가 아니라 실제 객체 가져옴
        System.out.println("refMem2.getClass() = " + refMem.getClass());

        System.out.println("프록시 객체와 엔티티가 동일한가 = " + (refMem.getClass() == findMem.getClass()));
        //true


        //(2) 프록시 객체 조회 후 실제 엔티티 조회
        Member refMem2 = em.getReference(Member.class, member.getId()); //프록시 객체 먼저 가져옴
        System.out.println("refMem3.getClass() = " + refMem2.getClass());

        Member findMem2 = em.find(Member.class, member.getId()); //프록시 객체 조회
        System.out.println("findMem3.getClass()" + findMem2.getClass());

        System.out.println("프록시 객체와 엔티티가 동일한가 = " + (refMem2.getClass() == findMem2.getClass()));
        //true

        /*
        jpa는 한 트랜잭션 내 실제 엔티티 객체와 프록시 객체 비교 연산 동작 완전성 보장을 위해
        프록시 객체 조회 후 실제 엔티티를 조회하는 경우라도
        두 객체가 모두 프록시 객체를 반환하도록 함
        => 두 객체의 클래스 타입은 동일
         */

        System.out.println("instanceof = " + (refMem2 instanceof Member)); //true
        System.out.println("instanceof = " + (findMem2 instanceof Member)); //true


        //(3) 영속성 컨텍스트 도움을 받을 수 없는 준영속 상태의 프록시 객체 초기화 : LazyInitializationException
        Member refMem3 = em.getReference(Member.class, member.getId());
        em.detach(refMem3);
        System.out.println("refMem3 = " + refMem3.getId() + ":" + refMem3.getName());

        //(4) 프록시 관련 메서드 
    }


}
