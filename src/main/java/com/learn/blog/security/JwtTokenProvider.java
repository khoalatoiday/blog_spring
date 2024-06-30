package com.learn.blog.security;

import com.learn.blog.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String secretKey;

    @Value("${app-jwt-expiration-milliseconds}")
    private long secretKeyExpiredAt;

    // generate token from authentication
    public String generateToken(Authentication authentication) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MILLISECOND, (int) secretKeyExpiredAt);
        Date expiredDate = calendar.getTime();
        String userName = authentication.getName();
        String token = Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(expiredDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // decode token
    public String getUserName(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "INVALID TOKEN");
        }catch (ExpiredJwtException expiredJwtException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "TOKEN IS EXPIRED!");
        }catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "UNSUPPORT JWT!");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT CLAIMS IS EMPTY OR NULL!");
        }
    }
}
