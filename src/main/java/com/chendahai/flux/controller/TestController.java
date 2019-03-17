package com.chendahai.flux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {
    @GetMapping("get1")
    public String get1(){
        log.info("get1 start");
        String str = createStr();
        log.info("get1 end");
        return str;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some string";
    }

    @GetMapping("/get2")
    public Mono<String> get2(){
        log.info("get2 start");
        Mono<String> mono = Mono.fromSupplier(() -> createStr());
        log.info("get2 end");
        return mono;
    }

    // 需要指定类型text/event-stream，可以像流一样返回多次的数据
    @GetMapping(value="/get3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> get3(){
        Flux<String> flux = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data -- " + i;
        }));
        return flux;
    }


}
