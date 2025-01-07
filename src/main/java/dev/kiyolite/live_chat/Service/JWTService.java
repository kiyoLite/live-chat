/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author soyky
 */
@Service
public class JWTService {

    private String secretKey = System.getenv("JWT_KEY");

    public String getToken(UserDetails user) {
        Map claims = new HashMap<>();
        String token = create(user, claims);
        return token;

    }

    private String create(UserDetails user, Map claims) {
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date());
        short expirationTimeInHourse = 24;
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.HOUR_OF_DAY, expirationTimeInHourse);
        builder.setExpiration(expiration.getTime());
        Key key = getKey();
        SignatureAlgorithm keyAlgorithm = SignatureAlgorithm.HS256;
        builder.signWith(key, keyAlgorithm);

        String token = builder.compact();
        return token;

    }

    private Claims getClaims(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getKey()).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims;
    }

    private <T> T getSingleClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaims(token);
        T SingleClaim = claimsResolver.apply(claims);
        return SingleClaim;
    }

    public String getSubject(String token) {
        try {
            return getSingleClaim(token, claims -> claims.getSubject());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private Date getExpiration(String token) {
        return getSingleClaim(token, claims -> claims.getExpiration());
    }

    public boolean isValid(UserDetails user, String token) {
        String userName = user.getUsername();
        String tokenUserName = getSubject(token);
        boolean areNamesEquals = userName.equals(tokenUserName);
        boolean isExpired = isExpired(token);
        return areNamesEquals && !isExpired;
    }

    private boolean isExpired(String token){
        Date now = new Date();
        Date expiration =  getExpiration(token);
        boolean isTokenExpired = now.after(expiration);
        return isTokenExpired;
    }

    private Key getKey() {
        byte[] encodeKey = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(encodeKey);
        return key;
    }

}
