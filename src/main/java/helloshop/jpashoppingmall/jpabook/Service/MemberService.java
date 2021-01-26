package helloshop.jpashoppingmall.jpabook.Service;

import helloshop.jpashoppingmall.jpabook.domain.Member;
import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    *회원가입
    */
    @Transactional
    public Member join(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    /*
    *회원 전체 조회
    */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    /*
    회원 한 명 조회
    */
    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }
}
