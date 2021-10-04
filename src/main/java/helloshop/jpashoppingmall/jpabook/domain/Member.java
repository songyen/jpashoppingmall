package helloshop.jpashoppingmall.jpabook.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter @Setter
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
    private List<Order> orders;

    @Builder
    public Member(String email, String passwd, Address address) {
        this.email = email;
        this.passwd = passwd;
        this.address = address;
    }

    //==연관관계 메서드==//
    public void addOrder(Order order){
        orders.add(order);
        order.setMember(this);
    }
}
