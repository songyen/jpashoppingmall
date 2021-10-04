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
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/signUp")
    public String createForm(Model model){
        model.addAttribute("memberDTO",new MemberDTO());
        return "login/createMemberForm";
    }

    @PostMapping("/signUp")
    public String create(@Valid MemberDTO memberDTO, BindingResult result){
        if(result.hasErrors()){
            return "login/createMemberForm";
        }
        /*
        List<Member> hasEmail = memberRepository.findByEmail(form.getEmail());
        //이메일 중복 불가
        if(!hasEmail.isEmpty()){
            return "login/emailDuplicate";
        }
        Address address = new Address(form.getCity(), form.getStreet(),form.getZipcode());

        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setAddress(address);
         */

        memberService.join(memberDTO);
        return "login/login";
    }

    @GetMapping("/shopLogin")
    public String login(){
        return "login/login";
    }


    @GetMapping("/denied")
    public String denied(){
        return "login/denied";
    }

}
