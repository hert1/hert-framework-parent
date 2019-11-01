package com.hert.gateway.filter;

import com.hert.gateway.config.UrlResolver;
import com.hert.gateway.config.UrlWhileList;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        //白名单
        if (UrlResolver.check(UrlWhileList.getUrlList(), url)) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get("hert-auth");
        Assert.isTrue(!CollectionUtils.isEmpty(tokens), "401");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
