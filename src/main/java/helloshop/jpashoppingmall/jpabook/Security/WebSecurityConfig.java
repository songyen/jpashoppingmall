package helloshop.jpashoppingmall.jpabook.Security;

import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtAuthenticationFilter;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthenticationException;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 보안토큰 disable
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰 기반 인증이므로 세션 역시 사용안함
                .and()
                .authorizeRequests() //요청에 대한 사용권한 체크
                .antMatchers("/**").permitAll()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),memberRepository))
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationException())
                .accessDeniedHandler(new AuthorizationException());
    }
}