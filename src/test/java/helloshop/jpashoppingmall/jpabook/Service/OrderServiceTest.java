package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.Exception.NotEnoughStockException;
import helloshop.jpashoppingmall.jpabook.domain.Address;
import helloshop.jpashoppingmall.jpabook.domain.Item.Book;
import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.domain.Order;
import helloshop.jpashoppingmall.jpabook.domain.OrderStatus;
import helloshop.jpashoppingmall.jpabook.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Item book = createBook("시골 JPA",10000,10);
        int orderCount=2;

        //when
        Order order = orderService.order(member.getId(), book.getId(),orderCount);
        Long orderId = order.getId();

        //then
        Order getOrder = orderRepository.find(orderId);
        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", 10000*orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄여야 한다", 8, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 2;

        Order order = orderService.order(member.getId(),item.getId(),orderCount);

        //when
        orderService.cancleOrder(order.getId());
        //then
        Assertions.assertEquals(OrderStatus.CANCLE, order.getStatus(),"주문 취소시 상태는 CANCEL이다.");
        Assertions.assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 11;
        //when
        orderService.order(member.getId(), item.getId(),orderCount);

        //then
        Assertions.fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    private Item createBook(String name,int orderPrice,int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

}
