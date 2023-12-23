package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery") // delivery 의 입장에서 order 는 하나의 배송정보가 하나의 주문만 가지기 때문에 1 : 1 관계이다.
    // 연관관계 거울이다 라고 하면 누구에 의해 매핑이 되었는지를 적어준다. 여기서 delivery 는 정확하게 order Table 에 있는 delivery 필드에 의해서 나는 매핑된거다 라고 할 수 있다.
    private Order order;

    @Embedded // 내장타입이라 사용한다.
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // ENUM [READY(준비), COMP(배송)]
}
/*
ORDINAL 은 1,2,3,4 ... 이렇게 숫자가 들어간다.
STRING 은 중간에 XXX 가 들어가도 밀리는게 없다.

ORDINAL 를 사용하면 READY 를 1, COMP 를 2 로 해서 사용하는데 만약 READY 와 COMP 중간에 XXX 가 들어가면 1,2,3 이 되는게 아니라
1,2,2 이렇게 되기 때문에 문제가 발생할 확률이 매우 높다.
그래서 STRING 으로 변경해줘야 중간에 XXX 가 들어가도 1, 2, 3 순차적으로 나오게 된다.
 */
