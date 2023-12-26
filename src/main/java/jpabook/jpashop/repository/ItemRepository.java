package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /*
    상품 저장
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item); // `id` 가 없으면 신규로 보고 `persist()` 실행
        } else {
            em.merge(item); // `id` 가 있으면 이미 데이터베이스에 저장된 엔티티를 수정한다고 보고, `merge()` 를 실행
        }
    }

    /*
    상품 한개 조회
     */
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    /*
    상품 여러개 조회
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
