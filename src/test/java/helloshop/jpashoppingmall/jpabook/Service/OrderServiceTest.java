package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.domain.Address;
import helloshop.jpashoppingmall.jpabook.domain.Item.Book;
import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.domain.Order;
import helloshop.jpashoppingmall.jpabook.domain.OrderStatus;
import helloshop.jpashoppingmall.jpabook.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
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
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("jpa");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

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

        //when

        //then
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given

        //when

        //then
    }
}
