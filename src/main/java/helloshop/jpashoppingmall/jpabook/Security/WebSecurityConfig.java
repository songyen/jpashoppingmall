package helloshop.jpashoppingmall.jpabook.Security;

import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtAuthenticationFilter;
import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtAuthorizationFilter;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthenticationException;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthorizationException;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 보안토큰 disable
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰 기반 인증이므로 세션 역시 사용안함
                .and()
                .httpBasic().disable()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home")
                .and()
//                .logout()
//                    .logoutSuccessUrl("/home")
//                    .invalidateHttpSession(true)
//                .and()
                .authorizeRequests() //요청에 대한 사용권한 체크
                    .antMatchers("/**").permitAll()
//                    .antMatchers("/members").hasRole("ADMIN")
//                    .antMatchers("/items/new","items/{itemId}/edit").hasRole("SELLER")
//                    .antMatchers("/items","/order/**","/orders").hasRole("USER")
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),memberRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),memberRepository))
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationException())
                .accessDeniedHandler(new AuthorizationException());
    }
}