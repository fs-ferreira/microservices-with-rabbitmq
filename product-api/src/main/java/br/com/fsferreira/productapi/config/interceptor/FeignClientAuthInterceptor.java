package br.com.fsferreira.productapi.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import static br.com.fsferreira.productapi.config.RequestUtil.getCurrentRequest;

@Component
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private final static String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        var currentRequest  = getCurrentRequest();
        requestTemplate.header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
        requestTemplate.header(TRANSACTION_ID, currentRequest.getHeader(TRANSACTION_ID));
    }
}
