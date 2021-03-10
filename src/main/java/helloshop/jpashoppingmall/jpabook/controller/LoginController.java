package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtTokenProvider;
import helloshop.jpashoppingmall.jpabook.domain.LoginDTO;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping( "/login")
    public String loginForm() {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO user){
        log.info("로그인요청");
        Member member = memberRepository.findByEmail(user.getEmail()).get(0);
        log.info("로그인 요청한 사용자 탐색");
        if(member.getEmail().isEmpty()){
            throw new IllegalArgumentException("가입되지 않은 Email입니다.");
        }
        if(!bCryptPasswordEncoder.matches(user.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

    }
}
