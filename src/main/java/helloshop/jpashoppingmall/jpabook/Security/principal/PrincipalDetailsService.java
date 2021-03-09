package helloshop.jpashoppingmall.jpabook.Security.principal;

import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        List<Member> member = memberRepository.findByEmail(username);
        if(member.isEmpty()){
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }else{
            return new PrincipalDetails(member.get(0));
        }
    }
}
