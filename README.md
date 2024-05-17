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


[CRUD](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/CrudWithJpa.java)

## [Persistence Context](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/PersistenceContextLife.java) <br>
엔티티를 영구 저장하는 환경 (논리적 개념)<br>
1. 1차 캐시 저장 후 transaction commit 시 객체 변경 정보를 모아 한꺼번에 쿼리 날림
  *같은 EntityManager에서 비교하는 게 아니라면 다른 객체간 비교가 되는 것

  1차 캐시에 없다면 db에서 조회하는 쿼리 실행 <br>
  [1차 cache]()
  
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

## DDL 사용 x 
-create : 테이블 삭제 후 생성  
-create-drop : 종료 시점 테이블 삭제    
-update  
-validate : 정상 매핑 확인  

## Mapping

[Field, Column]()

키 매핑
IDENTITY : key 바로 받도록 예외 허용, SEQUENCE : db로부터 한번에 받아올 양 정해둠, TABLE : key 생성용, (DEFAULT)AUTO
[Key]()

연관관계 매핑 
- 연관관계의 주인
  변경이 되는 쪽 @JoinColumn <-> 읽기만 가능 mappedBy = ""
  foreign key(fk) 가 있는 쪽
- 연관관계 메서드
  양쪽 방향 모두 값을 지니도록 역할
[연관관계 메서드](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/domain/Member.java)
[연관관계 문제](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/EntityMapping.java)

- 연관관계 종류
  N : 1 (fk = 연관관계 주인)
  1 : N (1이 주인이지만 N이 fk를 가지도록)
  1 : 1 (둘다 주인 가능)
  N : M (실무 x -> 연결 테이블용 엔티티 둠)

### 심화 매핑
- [상속관계](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/domain/Movie.java)
  1. joined
  2. single_table
  3. table_per_class
- [mapped super class](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/domain/BaseEntity.java)

## [proxy와 연관관계](https://github.com/pcochoco/jpa_basic/blob/main/src/main/java/com/example/jpa_basic/ProxyExample.java) 
실제 클래스 상속 -> 겉모양이 같음 
실제 값 필요 시까지 db 조회 미룸
연관 entity를 모두 가져올 필요 없을 시 사용 
- 프록시 객체는 처음 사용할 때 한번만 초기화
- 프록시 객체 초기화 시 실제 엔티티로 바뀌는 게 아니라 참조
- 원본 엔티티 상속 상태 : instance of를 사용 (다른 transaction일 때)
- 영속성 컨텍스트에 이미 엔티티가 있으면 프록시 호출에도 엔티티 반환

### 영속성 전이 : 영속 상태를 연관 엔티티도 같이 유지할 때 
### 고아 객체 : 부모와 연관관계가 끊어진 엔티티 
=> 둘다 소유하는 개념의 엔티티에 적용 


### 값 타입
1. 엔티티 타입 -> 수정되어도 식별자로 추적
2. 값 타입 -> 엔티티 생명주기 의존, 단순 값
     - 기본 값 : 보통 수정 불가
     - Embedded : 기본 값 모아 새 값으로
     - 불변 객체 : 공유되더라도 수정 불가
     - collection : 값 타입 하나 이상 지정 시 List, ... -> db로 저장할 별도 테이블 (권장 x)
         - 식별자 개념 x
         - 변경 시 추적 x
         - 변경 시 엔티티 값 삭제 후 저장
         - null x -> 중복 방지 column pk
       => 1대다 연관관계로 설정
  
##JPQL 
sql 추상화 -> 엔티티 검색
- 엔티티와 속성은 대소문자 구별
- jpql 키워드는 대소문자 구별 x
- entity 이름 사용
- 별칭 필수 (as 생략)
- 반환타입 명확 : TypedQuery (x : Query)
- 반환값 1개 이상 : getResultList (x : getSingleResult)
- 파라미터 =:


1. projection : select 조회, distinct 활용
2. pasing : 필요한 데이터만
3. join, subquery, 조건식, 함수
4. 경로표현식
5. fetch join
6. 다형성 쿼리
7. named 쿼리
8. 벌크 연산
