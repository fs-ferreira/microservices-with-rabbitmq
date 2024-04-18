package br.com.fsferreira.productapi.config;

import br.com.fsferreira.productapi.config.exception.GenericServerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {
    public static HttpServletRequest getCurrentRequest() {
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
