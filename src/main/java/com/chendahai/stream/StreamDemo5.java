package com.chendahai.stream;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamDemo5 {
    public static void main(String[] args) {
        // 调用parallel产生并行流，一次打印四行
        // IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count();

        // 实现如下效果：先并行，再串行。
        // 多次调用parallel，sequential是以最后一次为准
//        IntStream.range(1,100)
//                // 产生并行流
//                .parallel().peek(StreamDemo5::debug)
//                // 产生串行流
//                .sequential().peek(StreamDemo5::debug2)
//                .count();


        // 默认的线程数目是cpu的个数
        // 使用如下属性可以改变默认的线程数
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","6");
//        // 并行
//        IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count();


        // 自定义线程池  ForkJoinPool-1-worker
        ForkJoinPool pool = new ForkJoinPool(3);
        pool.submit(() -> IntStream.range(1,100).parallel()
                .peek(StreamDemo5::debug).count());
        pool.shutdown();
        synchronized (pool){
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void debug(int i){
        System.out.println(Thread.currentThread().getName() + "debug "+i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void debug2(int i){
        System.err.println("debug2 "+i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1(){
        IntStream.range(1,100).parallel().peek(StreamDemo5::debug).count();
    }

}
