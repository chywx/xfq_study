package com.chendahai.flux.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReactorDemo {
    public static void main(String[] args) {
        // reactor = jdk8 stream + jdk9 reactive stream
        // mono 0-1个元素
        // flux 0-n个元素
        String [] strs = {"1","2","3"};
        // 定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                // 保存订阅关系，需要用他来给发布者响应
                this.subscription = subscription;
                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                // 接收到一个数据，处理
                System.out.println("接收到数据：" + integer);
                // 睡眠3秒
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 处理完毕之后再调用request请求下一个数据
                this.subscription.request(1);
                // cancel不在请求，接收
//                this.subscription.cancel();
            }


            @Override
            public void onError(Throwable throwable) {

                // 异常操作
                throwable.printStackTrace();
                // 出现异常，不再请求
                this.subscription.cancel();

            }

            @Override
            public void onComplete() {

                // 数据处理完毕，发布者关闭了
                System.out.println("处理完毕！");
            }
        };

        // 这里是jdk8的stream
        Flux.fromArray(strs).map(s -> Integer.parseInt(s))
                // 这里是jdk9的reactive stream
                .subscribe(subscriber);

    }
}
