package tb.util;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import tb.service.Impl.AdServiceImpl;
import tb.service.Impl.AuServiceImpl;
import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.CuServiceImpl;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;


public class myJwt {
    /**
     * 过期时间(单位:秒)
     */
    public static final int ACCESS_EXPIRE = 60 * 60;
    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private final static String SECRET = "adsffaskjfasfkjaldsaffaadsffsaaaa";
    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "Liao";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "Peripherals";

    private String token;
    /*
    这些是一组预定义的声明，它们 不是强制性的，而是推荐的 ，以 提供一组有用的、可互操作的声明 。
    iss: jwt签发者
    sub: jwt所面向的用户
    aud: 接收jwt的一方
    exp: jwt的过期时间，这个过期时间必须要大于签发时间
    nbf: 定义在什么时间之前，该jwt都是不可用的.
    iat: jwt的签发时间
    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */


    public myJwt(String token) {
        this.token = token;
    }

    public myJwt() {

    }

    public static String createToken(Map<String, String> claims) {

        String username = claims.get("username");
        String role = claims.get("role");
        String id = claims.get("id");
        String sessionId = claims.get("sessionId");

        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息payload
                .claim("username", username)
                .claim("role", role)
                .claim("id", id)
                .claim("sessionId", sessionId)
                //.claim("phonenum",phonenum)
                // 过期日期
                .expiration(exprireDate)
                //签发者
                .issuer(JWT_ISS)
                // 签发时间
                .issuedAt(new Date())
                // 签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }


    //因为判断时要调用非static方法，这里的judgeToken不设置为static
    public boolean judgeToken() {

//        //TODO
//        if(this.token!=null)return true;//测试用
//        System.out.println("测试：token自动返回true");


        try {
            Claims claim = parsePayload(this.token);
            String role = (String) claim.get("role");
            String username = (String) claim.get("username");

            Date date = claim.getExpiration();

            if (date.after(new Date())) {
                //token未过期
                switch (role) {
                    case "AD":
                        if (new AdServiceImpl().existUsername(username).equals("yes")) {
                            //存在，correct
                            return true;
                        }
                        break;
                    case "AU":
                        if (new AuServiceImpl().existUsername(username).equals("yes")) {
                            //存在，correct
                            return true;
                        }
                        break;
                    case "CS":
                        if (new CsServiceImpl().existUsername(username).equals("yes")) {
                            //存在，correct
                            return true;
                        }
                        break;
                    case "CU":
                        if (new CuServiceImpl().existUsername(username).equals("yes")) {
                            //存在，correct
                            return true;
                        }
                        break;
                }
            } else {
                //其他原因
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
        return false;

    }

    public String updateTokenTime() {
        Claims claim = parsePayload(this.token);
        String role = (String) claim.get("role");
        String username = (String) claim.get("username");
        String id = (String) claim.get("id");
        String sessionId = (String) claim.get("sessionId");

        Map<String, String> m = new HashMap<>();
        m.put("role", role);
        m.put("username", username);
        m.put("id", id);
        m.put("sessionId", sessionId);

        return createToken(m);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getValue(String Key) {
//        if(this.token!=null)return null;
        Claims claim = parsePayload(this.token);
        return claim.get(Key);
    }
}


