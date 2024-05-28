package com.pulien.service_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PreserveHeadersGatewayFilterFactory extends AbstractGatewayFilterFactory<PreserveHeadersGatewayFilterFactory.Config> {

    public PreserveHeadersGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

            HttpHeaders filteredRequestHeaders = new HttpHeaders();
            filteredRequestHeaders.addAll(requestHeaders);

            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.addAll(filteredRequestHeaders)).build()).build());
        };
    }

    public static class Config {
        // You can add configuration properties here if needed
    }
}
