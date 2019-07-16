package com.zhao.platform.utils;


import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * @author zhaoyanjiang-pc
 */
public class JwtUtils {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;
    private static final long TOKEN_EXPIRED_TIME1 = 1000 * 60 * 60;

    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    public static final String jwtId = "tokenId";

    public static final String HEAD_TOKEN = "Authorization";

    public static String createJWT(String userId){
        return createJWT(null,userId);
    }
    /**
     * 创建JWT
     */
    public static String createJWT(Map<String, Object> claims, String userId) {
        /* 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。 */
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Date now = new Date(System.currentTimeMillis());

        SecretKey secretKey = generalKey();
        /* 生成JWT的时间 */
        long nowMillis = System.currentTimeMillis();
        /* 下面就是在为payload添加各种标准声明和私有声明了 */
        /* 这里其实就是new一个JwtBuilder，设置jwt的body */
        JwtBuilder builder = Jwts.builder();
                /* 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的 */
                if(claims != null){
                    builder = builder.setClaims(claims);
                }

                /* 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。 */
        builder = builder.setId(jwtId)
                .setSubject(userId)
                /* iat: jwt的签发时间 */
                .setIssuedAt(now)
                /* 设置签名使用的签名算法和签名使用的秘钥 */
                .signWith(signatureAlgorithm, secretKey);
        long expMillis = nowMillis + TOKEN_EXPIRED_TIME1;
        Date exp = new Date(expMillis);
        /* 设置过期时间 */
        builder.setExpiration(exp);
        return builder.compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        /* 签名秘钥，和生成的签名的秘钥一模一样 */
        SecretKey key = generalKey();
        Claims claims;
        try {
            /* 得到DefaultJwtParser */
            claims = Jwts.parser()
                    /* 设置签名的秘钥 */
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (Exception e1){
            claims = null;
        }
        /* 设置需要解析的jwt */
        return claims;

    }
    public static String parseUserId(String token){
        return verifyJwt(token).getSubject();
    }
}
