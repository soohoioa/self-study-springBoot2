package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items") // item 의 입장에서 list 는 여러개의 아이템이 여러개의 카테고리를 가지기 때문에 N : N 관계가 된다.
    // 연관관계 거울이다 라고 하면 누구에 의해 매핑이 되었는지를 적어준다. 여기서 items 는 정확하게 Category Table 에 있는 items 필드에 의해서 나는 매핑된거다 라고 할 수 있다.
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /*
    stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*
    stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
/*
JOINED 는 가장 정교화된 스타일로 하는것
SINGLE_TABLE 은 한 테이블에 모두 사용한는것
TABLE_PER_CLASS 는 예를들어 Book, Album, Movie 3개만 나오는 것
 */

/*
- `addStock()` 메서드는 파라미터로 넘어온 수만큼 재고를 늘린다.
  - 이 메서드는 재고가 증가하거나 상품 주문을 취소해서 재고를 다시 늘려야 할 때 사용한다.
- `removeStock()` 메서드는 파라미터로 넘어온 수만큼 재고를 줄인다.
  - 만약 재고가 부족하면 예외가 발생한 다. 주로 상품을 주문할 때 사용한다.
 */