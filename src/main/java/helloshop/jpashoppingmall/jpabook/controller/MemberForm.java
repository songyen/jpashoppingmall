package helloshop.jpashoppingmall.jpabook.controller;

import helloshop.jpashoppingmall.jpabook.domain.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message="이메일은 필수입니다.")
    private String email;
    private String passwd;
    private Authority authority;

    private String city;
    private String street;
    private String zipcode;
}
