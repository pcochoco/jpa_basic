package com.example.jpa_basic.doamin;

import jakarta.persistence.*;

/*상속관계 매핑 : 슈퍼타입과 서브타입 관계가 객체 상속과 유사
    1. joined : entity를 각 table로 변환
    정규화, 효율적 <-> 복잡, 성능 저하

    2. single_table : 하나의 table로
    조회 성능 빠름 <-> column은 null 허용, table 크기가 커짐

    3. table_per_class : 구현 클래스마다 table (권장 x)

 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED) //상속관계 joined 전략으로
@DiscriminatorColumn //부모 엔티티로 어떤 자식 엔티티를 가리키는지 알 수 있도록 DTYPE column 추가
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
