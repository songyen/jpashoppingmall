package helloshop.jpashoppingmall.jpabook.domain;

import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id@GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private int orderPrice;
    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item,int orderPrice,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    /*
    *주문 상품 취소 -> 재고 원복
     */
    public void cancle() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /*
    * 주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
