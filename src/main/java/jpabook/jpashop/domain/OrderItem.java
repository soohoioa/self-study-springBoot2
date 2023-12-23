package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne // OrderItem 의 입장에서 item 은
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne // OrderItem 의 입장에서 order 는 여러개의 아이템을 하나의 주문에 담을 수 있어 N : 1 관계이다.
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    private int orderPrice; // 주문 가격
    private int count; // 주문 수랑
}
