package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.Service.ItemService;
import helloshop.jpashoppingmall.jpabook.Service.MemberService;
import helloshop.jpashoppingmall.jpabook.Service.OrderService;
import helloshop.jpashoppingmall.jpabook.domain.Item.Item;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.domain.Order;
import helloshop.jpashoppingmall.jpabook.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findAll();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "orders/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId,itemId,count);

        return ("redirect:/orders");
    }
    /*
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "orders/orderList";
    }*/
}
