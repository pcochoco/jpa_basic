package com.example.jpa_basic;

import com.example.jpa_basic.doamin.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/*persistence context의 life cycle
비영속 - new, transient
준영속 - detached
영속 - managed
삭제 - removed

 */
public class PersistenceContextLife {
    public static void persistence_context_life_cycle(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hi");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Member member = new Member();
        member.setId(1L);
        member.setName("A");

        //영속 : 객체 저장
        em.persist(member);

        //준영속 : 분리
        em.detach(member);

        //제거
        em.remove(member);

        /*1차 캐시 예시
            같은 entity manager에서 비교하는 경우 같은 객체,
            아닌 경우 다른 객체가 됨 -> 조회 결과는 같다고 뜨지만
         */
        em.persist(member);
        //1차 캐시에서 조회 : query 필요 없음
        Member findMem1 = em.find(Member.class, "A");
        //db 조회 (1차에 없기 때문)
        Member findMem2 = em.find(Member.class, "B");

        /*transactional writed behind 예시
        한 transaction 기준 commit 전까지 sql 지연 저장소에 변경 정보 저장
         */

        em.persist(member);

        em.getTransaction().commit(); //실제 db sql 보냄

        /*
        dirty checking
        1차 cache의 snapshot 기준 변경 정보에 따라 sql 쓰기 지연 저장소에 저장

        flush란 변경 내용 db에 반영
        em.flush 호출, transaction commit, jpql 실행 시 동작 
         */
        member.setName("B");
        member.setName("C"); //sql 안보내짐
        em.getTransaction().commit(); //commit 되는 순간 flush

    }
}
