package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // Table 를 사용해줘서 이름을 orders 로 변경해준다. 안그러면 괸례로 order 을 사용한다.
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private long id;

    @ManyToOne // order 의 입장에서 member 는 여러개의 주문을 회원 하나의 회원이 가질 수 있어 N : 1 관계이다.
    private Member member;
    // 연관관계 주인은 ForeignKey 가 가까운 곳으로 하면된다.
    // order 에 있는 member 를 변경하면 order 자신의 테이블안에 있는 걸 바꾸니까 내 테이블에 있는 컬럼이 업데이트가 되는구나 한다.

    @OneToMany(mappedBy = "order") // order 의 입장에서 orderItem 은 한개의 주문에 여러개의 주문 아이템을 가질 수 있어 1 : N 관계이다.
    // 연관관계 거울이다 라고 하면 누구에 의해 매핑이 되었는지를 적어준다. 여기서 order 는 정확하게 orderItem 에 있는 order 필드에 의해서 나는 매핑된거다 라고 할 수 있다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne // order 의 입장에서 delivery 는 하나의 주문이 하나의 배송정보만 가지기 때문에 1 : 1 관계이다.
    @JoinColumn(name = "delivery_id") // 매핑을 무엇으로 하려고 하는지를 적어준다. 여기서는 delivery_id 로 작성했고, ForeignKey 가 된다.
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}
/*
ORDINAL 은 1,2,3,4 ... 이렇게 숫자가 들어간다.
STRING 은 중간에 XXX 가 들어가도 밀리는게 없다.

ORDINAL 를 사용하면 ORDER 를 1, CANCEL 를 2 로 해서 사용하는데 만약 ORDER 와 CANCEL 중간에 XXX 가 들어가면 1,2,3 이 되는게 아니라
1,2,2 이렇게 되기 때문에 문제가 발생할 확률이 매우 높다.
그래서 STRING 으로 변경해줘야 중간에 XXX 가 들어가도 1, 2, 3 순차적으로 나오게 된다.
 */
