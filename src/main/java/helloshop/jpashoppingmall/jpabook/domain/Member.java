package helloshop.jpashoppingmall.jpabook.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    private String passwd;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //==연관관계 메서드==//
    public void addOrder(Order order){
        orders.add(order);
        order.setMember(this);
    }



}
