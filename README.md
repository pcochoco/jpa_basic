# JPA 기본 정리
JPA는 객체끼리, 관계형 DB끼리 설계 후 mapping하기 위한 중간 프레임워크 역할 <br>
application과 JDBC 사이 동작에서 동작 <br>
[JPA setting](https://github.com/pcochoco/jpa_basic/commit/4b1a637e1a3119a1c44791c06217ccf0c3bb798d)

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
