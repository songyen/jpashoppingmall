//package helloshop.jpashoppingmall.jpabook.Service;
//
//import helloshop.jpashoppingmall.jpabook.domain.Member;
//import helloshop.jpashoppingmall.jpabook.repository.MemberRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class MemberServiceTest {
//    @Autowired MemberService memberService;
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    public void 회원가입() throws Exception{
//        Member member = new Member();
//        member.setEmail("test@shop.kr");
//
//        assertEquals(member, memberService.join(member));
//    }
//    @Test
//    public void 중복_회원_예외() throws Exception{
//        Member member1 = new Member();
//        member1.setEmail("test@shop.kr");
//
//        Member member2 = new Member();
//        member2.setEmail("test@shop.kr");
//
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//        }
//        catch(IllegalStateException e){
//            return;
//        }
//
//    }
//}