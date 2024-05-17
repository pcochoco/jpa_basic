package com.example.jpa_basic;

import com.example.jpa_basic.domain.Member;
import com.example.jpa_basic.domain.MemberDto;
import jakarta.persistence.*;

import java.util.List;

//JPQL은 SQL의 추상화, 엔티티 검색
public class JPQLExample {
    public static void UseQuery(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //반환 타입이 명확한 경우
        TypedQuery<Member> typedQuery = em.createQuery("select m from Member as m", Member.class);

        //반환 타입이 불명환 경우
        Query query = em.createQuery("select m.name, m.age from Member m");

        //query 결과 하나 이상
        List<Member> resultList = query.getResultList();
        //결과가 하나인 경우
        //Member singleResult = query.getSingleResult();

        //파라미터
        TypedQuery<Member> typedQuery2 = em.createQuery("select m from M as m where m.name =:name", Member.class)
                .setParameter("name", "memberA");

        //projection (select로 조회, distinct 활용)
        //Object[] 조회
        List<Object[]> resultList2 = em.createQuery("select m.name, m.id from Member m")
                .getResultList();
        for(Object[] o : resultList2){
            System.out.println("o = " + o[0] + "," + o[1]);
        }

        //dto 조회
        String dtoQuery = "select new jpa_basic.MemberDto(m.name, m.age) from Member m";
        List<MemberDto> dtoList = em.createQuery(dtoQuery, MemberDto.class)
                .getResultList();
        for(MemberDto m : dtoList){
            System.out.println("m = " + m.getName() + "," + m.getAge());
        }

        //pasing : 필요 데이터만 가져옴
        List<Member> pasingList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();


        //조건식
        //1. case
        String caseQuery =
                "select" +
                        "case when m.age > 10 then '학생요금'" +
                        "else '일반요금'"+
                        "end"+
                        "from Member m";
        //2. coalesce : null 아니면 반환, null 이면 두번째 파라미터
        String coalesceQuery = "select coalesce(m.name, '이름없는 회원') from Member m";

        //3.nullif : 파라미터 두 값이 같으면 null 반환, 아니면 첫번째 파라미터
        String nullifQuery = "select nullif(m.name, 'memberA') from Member m";

                
    }
}
