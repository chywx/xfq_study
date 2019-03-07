package com.chendahai.stream;

import java.util.Random;
import java.util.stream.Stream;

public class StreamDemo3 {
    public static void main(String[] args) {
        String str = "my name is 007";

        // 将str进行空格分割，获取长度大于2的单词的长度
        Stream.of(str.split(" ")).filter(s -> s.length()>2).map(s -> s.length()).forEach(System.out::println);

        System.out.println("-----------------------");

        // boxed()进行装箱操作，直接输出i为数字，想要输出字母需要进行强转
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed())
                .forEach(i -> {
                    System.out.println(i instanceof Integer);
                    System.out.println((char)i.intValue());
                });

        System.out.println("-----------------------");

        // peek 用于 debug，是个中间操作，和foreach是终止操作
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);

        System.out.println("-----------------------");

        // limit 使用，主要用于无限流
        new Random().ints().filter(i -> i>100 && i < 1000).limit(10).forEach(System.out::println);



    }
}
