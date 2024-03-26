package br.com.fsferreira.productapi.jwt.dto;

import br.com.fsferreira.productapi.config.validator.TypeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

public class JwtResponse {

    private UUID id;
    private String name;
    private String email;

    public JwtResponse() {
    }

    public JwtResponse(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtResponse that = (JwtResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    public static JwtResponse getUser(Claims jwtClaims) {
        try {
           return new ObjectMapper().convertValue(jwtClaims.get("authUser"), JwtResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
