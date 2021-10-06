package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping("")
    public String main(){
        return "index";
    }

    @GetMapping("home")
    public String home(){
        return "home";
    }

    @GetMapping("signUp")
    public String createForm(Model model){
        model.addAttribute("memberDTO",new MemberDTO());
        return "login/createMemberForm";
    }

    @PostMapping("signUp")
    public String create(@Valid MemberDTO memberDTO, BindingResult result){
        if(result.hasErrors()){
            return "login/createMemberForm";
        }
        memberService.join(memberDTO);
        return "login/login";
    }

    @GetMapping("shopLogin")
    public String dispLogin(){
        return "login/login";
    }
}
