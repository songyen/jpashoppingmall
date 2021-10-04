package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.controller.MemberDTO;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.domain.Role;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /*
    *회원가입
    */
    @Transactional
    public Member join(MemberDTO memberDTO){
        validateDuplicateMember(memberDTO);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
        return memberRepository.save(memberDTO.toEntity());
    }

    private void validateDuplicateMember(MemberDTO memberDTO) {
        List<Member> findMembers = memberRepository.findByEmail(memberDTO.getEmail());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 이메일 입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member user = memberRepository.findByEmail(email).get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getEmail().contains("@jpashop.co.kr")) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }

        return new User(user.getEmail(), user.getPasswd(), authorities);
    }
}
