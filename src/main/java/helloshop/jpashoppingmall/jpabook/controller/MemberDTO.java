package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.domain.Address;
import helloshop.jpashoppingmall.jpabook.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberDTO {
    @NotEmpty(message="이메일은 필수입니다.")
    private String email;
    private String passwd;

    private String city;
    private String street;
    private String zipcode;

    public Member toEntity(){
        Address address = new Address(city,street,zipcode);
        return Member.builder()
                .email(email)
                .passwd(passwd)
                .address(address)
                .build();
    }
}