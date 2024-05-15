package com.example.jpa_basic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

//jpa는 객체와 관계형 db 각각 설계 후 매핑하는 중간 프레임워크 역할
//interface들의 집합으로 특정 db에 종속 x -> db dialect 설정해 통합적 활용
//crud를 활용하는 예시

public class CrudWithJpa {
    public static void main(String[] args) {
        //EntityManagerFactory는 db당 하나씩 존재 -> connection 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //EntityManager은 connection마다 생성되어 transaction commit 후 소멸
        //다른 thread 간 공유되지 않음
        EntityManager em = emf.createEntityManager();

        //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();

        try {
            //create new member by persist
            Member newMem = new Member();
            newMem.setId(1L);
            newMem.setName("memberA");
            em.persist(newMem);

            //read member by find
            Member findMem = em.find(Member.class, 1L);
            System.out.println("findMember = " + findMem.getId() + ":" + findMem.getName());

            //update member by set
            Member updateMem = em.find(Member.class, 1L);
            updateMem.setName("memebrX");

            //delete member by remove
            Member deleteMem = em.find(Member.class, 1L);
            em.remove(deleteMem);

            //JPQL은 객체 대상으로 검색하는 객체 지향 쿼리
            //SQL은 db table 대상 쿼리 작성
            List<Member> members = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();
            for (Member member : members) {
                System.out.println("member = " + member.getId() + ":" + member.getName());
            }

            //transaction을 지원하는 쓰기 지연(transactional write behind) : sql을 모아 한번에 보냄

            tx.commit();
        } catch (Exception e) {
            //error 발생 시 db rollback
            tx.rollback();
        } finally {
            //db connection closing
            em.close();
        }
        emf.close();
    }
}
