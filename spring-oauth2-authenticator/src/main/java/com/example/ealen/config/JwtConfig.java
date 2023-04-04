package com.example.ealen.config;

import com.example.ealen.domain.entity.OauthAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtConfig {

    @Autowired
    private RedissonClient redissonClient;
    // 生成签名的时候使用的秘钥secret
    private static final String SECRETKEY = "KJHUhjjJYgYUllVbXhKDHXhkSyHjlNiVkYzWTBac1Yxkjhuad";

    // expirationDate 生成jwt的有效期，单位秒
    private static final long expirationDate = 10;

    // 生成JWT的时间
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //jwt的有效时间
    long expMillis = expirationDate;
    Date exp = new Date(expMillis);

    public String createToken (OauthAccount user){

        // 指定签名的时候使用的签名算法，也就是header那部分，jwt已经将这部分内容封装好了
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getClientId());
        claims.put("userName", user.getUsername());
        claims.put("password", user.getPassword());

        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了
        SecretKey key = generalKey(SECRETKEY + user.getPassword());

        // 生成签发人
        // json形式字符串或字符串，增加用户非敏感信息存储，如用户id或用户账号，与token解析后进行对比，防止乱用
        HashMap<String, Object> storeInfo = new HashMap<String, Object>();
        storeInfo.put("userId", user.getClientId());
        storeInfo.put("userName", user.getUsername());
        String subject = storeInfo.toString();

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 唯一随机UUID
                // 设置JWT ID：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                // jwt的签发时间
                .setIssuedAt(now)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);

        if (expirationDate >= 0) {
//                Date exp = new Date(expirationDate);
            builder.setExpiration(exp);
        }
        log.info("生成的token为：{}", builder.compact());
        return builder.compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return SecretKey
     */
    private static SecretKey generalKey(String stringKey) {
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解密token，获取声明的实体
     *
     * @param token 加密后的token
     * @return claims
     */
    public static Claims parseToken(String token, OauthAccount user) {
        // 签名秘钥，和生成的签名的秘钥要保持一模一样
        SecretKey key = generalKey(SECRETKEY + user.getPassword());

        // 获取私有声明
//        Claims claims =
        log.info("解析后的token为：{}",Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody());
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的token
                .parseClaimsJws(token).getBody();

//        return claims;
    }

    /**
     * 校验token
     *
     * @param token 加密后的token
     * @param user  用户信息
     * @return true|false
     */
    public Boolean verify(String token, OauthAccount user) {
        // 获取私有声明的实体
        Claims claims = parseToken(token, user);
        log.info(String.valueOf(claims.get("password").equals(user.getPassword())));
        return claims.get("password").equals(user.getPassword());
    }
}
