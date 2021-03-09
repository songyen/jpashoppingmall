package helloshop.jpashoppingmall.jpabook.Security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import helloshop.jpashoppingmall.jpabook.Security.principal.PrincipalDetails;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository){
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken;
        try{
            log.info("로그인 요청");

            Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
            authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(),member.getPassWd());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;

        }catch(BadCredentialsException e){
            log.info("비밀번호가 일치하지 않습니다.");
        }catch (LockedException e){
            log.info("계정이 잠겨 있습니다.");
        }catch (DisabledException e){
            log.info("계정이 비활성화 되어있습니다.");
        }catch (AccountExpiredException e){
            log.info("계정 유효기간이 만료되었습니다.");
        }catch (CredentialsExpiredException e){
            log.info("계정 비밀번호 유효기간이 만료되었습니다.");
        }catch (Exception e){
            log.info("로그인 인증 과정 예외 발생");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, SecurityException{
        log.info("로그인 완료");

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("jwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("email",principal.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);

        log.info("JWT 토큰 생성 & 전달 완료");
    }
}
