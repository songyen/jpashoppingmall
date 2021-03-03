package helloshop.jpashoppingmall.jpabook.Security.JWT;

public interface JwtProperties {
    String SECRET = "jpashop";
    int EXPIRATION_TIME = 1000 * 60 * 30;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
