package helloshop.jpashoppingmall.jpabook.repository;

import helloshop.jpashoppingmall.jpabook.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
