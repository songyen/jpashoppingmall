package helloshop.jpashoppingmall.jpabook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class LoginController {
    @GetMapping(value = "/login")
    public String loginForm() {
        return "/login";
    }

    @PostMapping(value="/login")
    public String login(){
        return "redirect:/";
    }
}
