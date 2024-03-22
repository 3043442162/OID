package com.ainetwork.util;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    public static String SIGNATURE = "ainetwork0183";

    /**
     * 生成token
     *
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 7);
        builder.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 120)));
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 生成token
     *
     * @param map //传入payload
     * @param day token有效天数
     * @return 返回token
     */
    public static String getToken(Map<String, String> map, Integer day) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 7);
        builder.withExpiresAt(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * day)));
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     *
     * @param token
     */
    public static void verify(String token) {
        com.auth0.jwt.JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取token中payload
     *
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token) {
        return com.auth0.jwt.JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return 密钥
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SIGNATURE);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt jwt
     * @return Claims
     * @throws Exception 异常
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
