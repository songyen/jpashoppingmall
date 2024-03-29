package helloshop.jpashoppingmall.jpabook.config;

import helloshop.jpashoppingmall.jpabook.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        //static 디렉터리 하위 파일 목록은 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.httpBasic().and().authorizeRequests()
                    // 페이지 권한 설정
                    .antMatchers("/","/signUp","/shopLogin").permitAll()
                    .antMatchers("/members","/items/new").hasRole("ADMIN")
                    .antMatchers("/order").hasRole("USER")
                    .anyRequest().authenticated().and().csrf().ignoringAntMatchers("/orders/{orderId}/cancel");


        // 로그인 설정
        http.formLogin().loginPage("/shopLogin")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                .and() // 로그아웃 설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/shopLogout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
