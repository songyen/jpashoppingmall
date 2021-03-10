package helloshop.jpashoppingmall.jpabook.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    private String email;
    private String password;
}
