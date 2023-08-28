package com.qing.erp.gateway.controller;

import com.qing.erp.common.data.R;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(-2)
public class GlobalExceptionWebExceptionHandler implements ErrorWebExceptionHandler {

    private final List<HttpMessageWriter<?>> messageWriters;

    public GlobalExceptionWebExceptionHandler(ServerCodecConfigurer serverCodecConfigurer) {
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus httpStatus = ((ResponseStatusException) ex).getStatus();
        String errorMessage = ex.getMessage();

        exchange.getResponse().setStatusCode(httpStatus);

        R errorResponse = R.error(httpStatus.value(), errorMessage);

        return response(exchange, errorResponse);
    }

    private Mono<Void> response(ServerWebExchange exchange, R errorResponse) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();

        if (response.getStatusCode() == HttpStatus.OK) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        DataBuffer dataBuffer = bufferFactory.wrap(errorResponse.toString().getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
