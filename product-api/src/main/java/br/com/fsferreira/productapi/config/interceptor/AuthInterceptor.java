package br.com.fsferreira.productapi.config.interceptor;

import br.com.fsferreira.productapi.config.exception.AuthenticationException;
import br.com.fsferreira.productapi.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Autowired
    JwtService service;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(isOptionMethod(request)) {
            return true;
        }

        validateRouteHeaders(request);

        var authorizationHeader = request.getHeader(AUTHORIZATION);
        service.validateAuthorization(authorizationHeader);

        request.setAttribute("serviceid", UUID.randomUUID().toString());
        return true;
    }

    private void validateRouteHeaders(HttpServletRequest request){
        if(isEmpty(request.getHeader(TRANSACTION_ID))){
            throw new AuthenticationException("The transactionId header is required!");
        }
    }
    private boolean isOptionMethod(@NonNull HttpServletRequest request){
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
