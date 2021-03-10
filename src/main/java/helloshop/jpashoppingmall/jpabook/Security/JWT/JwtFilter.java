package helloshop.jpashoppingmall.jpabook.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthenticationException;
import helloshop.jpashoppingmall.jpabook.Security.principal.PrincipalDetails;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, SecurityException{
        log.info("로그인 완료");

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.createToken(principal.getUsername(), principal.getAuthorities().toString());
        response.addHeader("Authorization", "Bearer "+jwtToken);

        log.info("JWT 토큰 생성 & 전달 완료");
    }
}
