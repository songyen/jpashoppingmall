package helloshop.jpashoppingmall.jpabook.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO(String email, String password){
        this.email = email;
        this.password = password;
    }
}
