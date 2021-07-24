package helloshop.jpashoppingmall.jpabook.repository;
import helloshop.jpashoppingmall.jpabook.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public Order save(Order order){
        em.persist(order);
        return order;
    }

    public Order find(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(){
        return em.createQuery("select o from Order o",Order.class)
                .getResultList();
    }

    /*
    회원명, 주문 상태 별 검색
     */
   public List<Order> findAllByString(OrderSearch orderSearch){
       String jpql = "select o From Order o join o.member m";
       boolean isFirstCondition = true;
       //주문 상태 검색
       if (orderSearch.getOrderStatus() != null) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " o.status = :status";
       }
       //회원 이름 검색
       if (StringUtils.hasText(orderSearch.getMemberEmail())) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " m.email like :email";
       }
       TypedQuery<Order> query = em.createQuery(jpql, Order.class)
               .setMaxResults(1000);
       if (orderSearch.getOrderStatus() != null) {
           query = query.setParameter("status", orderSearch.getOrderStatus());
       }
       if (StringUtils.hasText(orderSearch.getMemberEmail())) {
           query = query.setParameter("email", orderSearch.getMemberEmail());
       }
       return query.getResultList();
    }

}

