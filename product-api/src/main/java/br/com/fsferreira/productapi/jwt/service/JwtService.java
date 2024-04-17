package br.com.fsferreira.productapi.jwt.service;

import br.com.fsferreira.productapi.config.exception.AuthenticationException;
import br.com.fsferreira.productapi.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token){
        try {
            var accessToken = getToken(token);
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes())).
                    build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            var user = JwtResponse.getUser(claims);

            if(ObjectUtils.isEmpty(user) || user.getId() == null){
                throw new AuthenticationException("User unauthorized!");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            throw new AuthenticationException("User unauthorized!");
        }
    }


    private String getToken(String token) {
        if(token == null || token.isEmpty()) {
            throw new AuthenticationException("The access token is invalid!");
        }

        if(token.contains(BEARER)) {
            return token.replace(BEARER, Strings.EMPTY);
        }

        return token;
    }

}
