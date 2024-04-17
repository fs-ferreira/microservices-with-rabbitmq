package br.com.fsferreira.productapi.config.interceptor;

import br.com.fsferreira.productapi.config.exception.GenericServerException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private final static String AUTHORIZATION = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        var currentRequest  = getCurrentRequest();
        requestTemplate.header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION) );
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericServerException("Error trying to get request!");
        }
    }
}
