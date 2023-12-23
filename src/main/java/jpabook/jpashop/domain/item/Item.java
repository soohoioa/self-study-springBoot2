package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 한 테이블에 모두 사용하는것
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 추상클래스로 만든다. 왜냐하면 구현체를 가지고 갈꺼기 때문이다.

    @Id
    @GeneratedValue
    @Column(name = "item_id") // 엔티티의 식별자는 id 를 사용하고 PK 컬럼명은 item_id 를 사용했다.
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;
}
/*
JOINED 는 가장 정교화된 스타일로 하는것
SINGLE_TABLE 은 한 테이블에 모두 사용한는것
TABLE_PER_CLASS 는 예를들어 Book, Album, Movie 3개만 나오는 것
 */