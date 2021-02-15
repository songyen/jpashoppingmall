package helloshop.jpashoppingmall.jpabook.domain.Item;

import helloshop.jpashoppingmall.jpabook.Exception.NotEnoughStockException;
import helloshop.jpashoppingmall.jpabook.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn
@Getter @Setter
public abstract class Item {
    @Id@GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    /*
    * stock증가
    */
    public void addStock(int quantity) {this.stockQuantity+=quantity;}
    /*
    *stock감소
     */
    public void removeStock(int quantity) {
        int restStock =this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }

}
