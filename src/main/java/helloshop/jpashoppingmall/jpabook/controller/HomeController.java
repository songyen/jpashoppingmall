package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.Service.MemberService;
import helloshop.jpashoppingmall.jpabook.domain.Address;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @RequestMapping("/main")
    public String main(){
        return "index";
    }

    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/signUp")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "login/createMemberForm";
    }

    @PostMapping("/signUp")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "login/createMemberForm";
        }
        //이메일 중복 불가
        List<Member> hasEmail = memberRepository.findByEmail(form.getEmail());
        if(!hasEmail.isEmpty()){
            return "login/emailDuplicate";
        }

        Address address = new Address(form.getCity(), form.getStreet(),form.getZipcode());

        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setAddress(address);

        memberService.join(member);
        return "index";
    }

    @GetMapping("/shopLogin")
    public String login(){
        return "login/login";
    }
}
