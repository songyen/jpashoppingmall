package helloshop.jpashoppingmall.jpabook.Security.JWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 JWT를 받아온다
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        //유효한 토큰인지 확인한다
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 (인증)정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /*
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
    }*/
}
