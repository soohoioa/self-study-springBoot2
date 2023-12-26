package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY) // category 의 입장에서 list 는 여러개의 카테고리가 여러개의 아이템을 가지기 때문에 N : N 관계가 된다.
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();
    /*
    객체는 다 컬랙션이 있어서 다대다 괸계가 가능한데 관계형 DB 는 컬렉션관계를 양쪽에 가질 수 있는게 아니기 때문에
    1 : N , N : 1 관계로 풀어내는 중간테이블이 필요하다.
     */

    @ManyToOne
    @JoinColumn(name = "parent_id") // 카테고리는 하나의 부모이다.
    private Category parent;

    @OneToMany(mappedBy = "parent")// 카테고리가 여러개의 자식을 가질 수 있다.
    private List<Category> child = new ArrayList<>();
    // 이름만 내꺼지 사실 다른 엔티티와 매핑하는거랑 똑같은 식으로 연관관계를 맺어주면 된다.

    //==연관 관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
/*
-  **참고**
  - 실무에서는 `@ManyToMany` 를 사용하지 말자
  - `@ManyToMany` 는 편리한 것 같지만, 중간 테이블( `CATEGORY_ITEM` )에 컬럼을 추가할 수 없고, 세밀하게 쿼리를 실행하기 어렵기 때문에 실무에서 사용하기에는 한계가 있다.
  - 중간 엔티티( `CategoryItem` ) 를 만들고 `@ManyToOne` , `@OneToMany` 로 매핑해서 사용하자.
 */