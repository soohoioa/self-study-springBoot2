package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 트랜잭션, 영속성 컨텍스트
@RequiredArgsConstructor // final 에 있는 필드만 가지고 생성자를 만들어준다.
public class MemberService {

    // @Autowired 생성자 Injection 많이 사용, 생성자가 하나면 생략 가능
    private final MemberRepository memberRepository;

    /*
    생성자 주입 : 필드 주입보다는 주입하기 쉽다.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /*
    회원가입
     */
    @Transactional // 변경
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // Exception 발생
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    Id 로 하나의 회원만 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
/*
- **참고**: 실무에서는 검증 로직이 있어도 멀티 쓰레드 상황을 고려해서 회원 테이블의 회원명 컬럼에 유니크 제약 조건을 추가하는 것이 안전하다.
- **참고**: 스프링 필드 주입 대신에 생성자 주입을 사용하자.
 */

/*
- 생성자 주입 방식을 권장
- 변경 불가능한 안전한 객체 생성 가능
- 생성자가 하나면, @Autowired 를 생략할 수 있다.
- final 키워드를 추가하면 컴파일 시점에 memberRepository 를 설정하지 않는 오류를 체크할 수 있다. (보통 기본 생성자를 추가할 때 발견)
 */

/*
- @AllArgsConstructor : 필드 모든것을 가지고 생성자 똑같은 것을 만들어준다.
- @RequiredArgsConstructor : final 에 있는 필드만 가지고 생성자를 만들어준다.
 */

/*
- readOnly = true : 데이터의 변경이 없는 읽기 전용 메서드에 사용한다.
  - 영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
  - 데이터베이스 드라이버가 지원하면 DB 에서 성능 향상
- 읽기 에는 가급적으로 readOnly = true 를 넣어주는게 좋다. 읽기가 아닌 쓰기에는 넣어주면 안된다.
 */