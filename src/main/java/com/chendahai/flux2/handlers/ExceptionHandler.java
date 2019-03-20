package com.chendahai.flux2.handlers;

import com.chendahai.flux2.exceptions.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        // 设置响应头为400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        // 设置返回类型
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        // 异常信息
        String errorMsg = toStr(throwable);
        DataBuffer wrap = response.bufferFactory().wrap(errorMsg.getBytes());
        Mono<Void> mono = response.writeWith(Mono.just(wrap));
        return mono;
    }

    private String toStr(Throwable throwable) {
        // 已知异常
        if(throwable instanceof CheckException){
            CheckException e = (CheckException) throwable;
            return e.getFieldName() + " 非法值 " + e.getFieldValue();
        }
        // 未知异常
        else{
            System.out.println("chy error");
            throwable.printStackTrace();
            return throwable.toString();
        }
    }
}
