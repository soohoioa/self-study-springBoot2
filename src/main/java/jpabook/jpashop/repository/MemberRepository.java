package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 예외 변환
public class MemberRepository {

    @PersistenceContext // 엔티티 메니저( `EntityManager` ) 주입
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) { // 조회
        return em.find(Member.class, id); // member 을 찾아서 반환.
    }

    public List<Member> findAll() { // 전체 조회
        return em.createQuery("select m from member m", Member.class)
                .getResultList(); // 첫번째는 JPql , 두번째는 반환타입 을 넣어준다.
    }

    public List<Member> findByName(String name) { // 이름으로 검색
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
/*
SQL 은 테이블을 대상으로 쿼리를 하는데 JPql 는 엔티티 객체를 대상으로 쿼리를 한다.
 */