package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // 엔티티의 식별자는 id 를 사용하고 PK 컬럼명은 member_id 를 사용했다.
    private Long id; // 회원 아이디

    private String name; // 회원 이름

    @Embedded // 내장타입을 포함했다 라고 어노테이션으로 매핑.
    private Address address; // 회원 주소

    @OneToMany(mappedBy = "member") // member 의 입장에서 list 는 하나의 회원이 여러개의 상품을 주문하기 때문에 1 : N 관계가 된다.
    // 연관관계 거울이다 라고 하면 누구에 의해 매핑이 되었는지를 적어준다. 여기서 member 는 정확하게 order Table 에 있는 member 필드에 의해서 나는 매핑된거다 라고 할 수 있다.
    @JoinColumn(name = "member_id") // 매핑을 무엇으로 하려고 하는지를 적어준다. 여기서는 member_id 로 작성했고, ForeignKey 가 된다.
    private List<Order> orders = new ArrayList<>(); // 회원 주문 목록

}
/*
- **참고**:
  - 엔티티의 식별자는 `id` 를 사용하고 PK 컬럼명은 `member_id` 를 사용했다.
  - 엔티티는 타입(여기서는 `Member` )이 있으므로 `id` 필드만으로 쉽게 구분할 수 있다.
  - 테이블은 타입이 없으므로 구분이 어렵다.
  - 그리고 테이블은 관례상 `테이블명` + `id` 를 많이 사용한다.
  - 참고로 객체에서 `id` 대신에 `memberId` 를 사용해도 된다.
  - 중요한 것은 **일관성**이다.
 */

/*
만약 내장타입을 사용할경우 @Embeddable 나 @Embedded 중 하나만 사용해도 되지만 보통은 두개를 같이 사용해준다.
두개를 같이 사용해주면 보기도 편하다.
 */