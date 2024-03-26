package br.com.fsferreira.productapi.config.interceptor;

import br.com.fsferreira.productapi.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    JwtService service;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(isOptionMethod(request)) {
            return true;
        }

        var authorizationHeader = request.getHeader(AUTHORIZATION);
        service.validateAuthorization(authorizationHeader);
        return true;
    }

    private boolean isOptionMethod(@NonNull HttpServletRequest request){
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
