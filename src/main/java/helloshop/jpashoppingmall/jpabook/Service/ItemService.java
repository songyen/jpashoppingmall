package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.domain.Item.Book;
import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import helloshop.jpashoppingmall.jpabook.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.find(itemId);
        findItem.setId(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item find(Long id){
        return itemRepository.find(id);
    }
}
