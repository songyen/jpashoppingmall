package helloshop.jpashoppingmall.jpabook.Security;

import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtAuthenticationFilter;
import helloshop.jpashoppingmall.jpabook.Security.JWT.JwtTokenProvider;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthenticationException;
import helloshop.jpashoppingmall.jpabook.Security.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();  //더블슬래시 허용
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
        web.httpFirewall(defaultHttpFirewall());  //더블슬래시 허용용
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 보안토큰 disable
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰 기반 인증이므로 세션 역시 사용안함
                .and()
                .httpBasic().disable()
//                .formLogin()
//                    .loginPage("/login").permitAll()
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/home")
//                .logout()
//                    .logoutSuccessUrl("/home")
//                    .invalidateHttpSession(true)
//                .and()
                .authorizeRequests() //요청에 대한 사용권한 체크
                    .antMatchers("/home","/members/new","/login","/orders").permitAll()
                    .antMatchers("/members","/items/new").hasRole("ADMIN")
                    .antMatchers("/order/**","/items/**").hasRole("USER")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationException())
                .accessDeniedHandler(new AuthorizationException());
    }
}