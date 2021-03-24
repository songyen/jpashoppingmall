package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtTokenProvider;
import helloshop.jpashoppingmall.jpabook.domain.LoginDTO;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping( "login")
    public String loginForm() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(@RequestParam String email, @RequestParam String password){
        log.info("로그인요청");
        Member member = memberRepository.findByEmail(email).get(0);
        System.out.println(member.getEmail());
        if(member.getEmail().isEmpty()){
            throw new IllegalArgumentException("가입되지 않은 Email입니다.");
        }
        log.info("로그인 요청한 사용자 탐색");

        if(!bCryptPasswordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }


        return jwtTokenProvider.createToken(member.getEmail(), member.getAuthority().toString());
    }
}
