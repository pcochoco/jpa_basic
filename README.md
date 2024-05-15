# JPA 기본 정리
JPA는 객체끼리, 관계형 DB끼리 설계 후 mapping하기 위한 중간 프레임워크 역할 <br>
application과 JDBC 사이 동작에서 동작 <br>
[JPA setting](https://github.com/pcochoco/jpa_basic/blob/main/src/main/resources/META-INF/persistence.xml)

## JPA를 사용하는 이유 
1. sql 중심 개발 -> 객체 중심으로 
2. 유지보수 : CRUD의 간결화 
3. 패러다임 불일치 해결 
  - 상속 : 객체를 테이블에 맞춰 분해 -> insert -> join 
  - 연관관계 : 객체(참조), table(fk) 
  - 객체 그래프 탐색과 엔티티 신뢰 문제 : 연관관계 여부가 sql 날린 시점에 따라 갈림
  - 객체 비교 : 새 객체 생성 -> 다른 객체 반환
4. 성능
  - 1차 캐시와 동일성 보장 : 1 transaction 내의
  - transactional write behind : transacion commit 시까지 sql을 모아 한번에 보냄
  - 지연로딩과 즉시로딩 : 객체의 로딩 시점
  - 데이터 접근 추상화 벤더 독립성 : JPA db dialect로 db간 통합 


[CRUD](https://github.com/pcochoco/jpa_basic/commit/96ac622eeb647ef23da9e5614c6fc77ecb14669b)

## Persistence Context
[Persistence Context]()
엔티티를 영구 저장하는 환경 (논리적 개념)<br>
1. 1차 캐시 저장 후 transaction commit 시 객체 변경 정보를 모아 한꺼번에 쿼리 날림
  *같은 EntityManager에서 비교하는 게 아니라면 다른 객체간 비교가 되는 것

2. transactional write behind
     쓰기 지연 sql 저장소에 sql 저장 후 commit 되는 순간 db로
3. dirty checking
   변경 시 1차 캐시 저장 snapshot과 비교 후 달라진 부분에 맞게 sql을 생성해 쓰기 지연 sql로 저장, commit 시 flush
   *flust : 변경 내용을 db에 반영 (em.flush, transaction commit, JPQL 실행) 
<영속성 생명주기> 
- new, transient (비영속) : 영속성 context와 관련 x
- managed (영속) : 객체 영구 저장 = db 관리
- detached (준영속) : 영속 상태 -> 분리
- removed (삭제) <br>

## DDL 

##Mapping
[Field, Column]()
[Key]()
[연관관계]()
