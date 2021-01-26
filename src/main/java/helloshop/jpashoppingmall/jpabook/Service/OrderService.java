package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.domain.*;
import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import helloshop.jpashoppingmall.jpabook.repository.ItemRepository;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import helloshop.jpashoppingmall.jpabook.repository.OrderRepository;
import helloshop.jpashoppingmall.jpabook.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
    *주문
     */
    @Transactional
    public Order order(Long memberId,Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.find(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());


        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member,delivery,orderItem);

        //주문 저장
        orderRepository.save(order);
        return order;
    }
    /*
    *주문 취소
     */
    @Transactional
    public void cancleOrder(Long orderId){
        Order order = orderRepository.find(orderId);
        order.cancle();
    }

    /*
    *검색

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }*/


}
