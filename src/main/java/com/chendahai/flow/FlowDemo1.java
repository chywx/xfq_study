package com.chendahai.flow;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * SubmissionPublisher
 * 失败，找不到SubmissionPublisher的jar包，即使自己下载了jdk9.
 */
public class FlowDemo1 {
    public static void main(String[] args) {
//        SubmissionPublisher

        Publisher<Integer> publisher = new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber subscriber) {

            }
        };

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

        // 发布者和订阅者建立订阅关系
        publisher.subscribe(subscriber);

        int data = 111;
    }
}
