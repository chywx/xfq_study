package com.chendahai.stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo4 {
    public static void main(String[] args) {
        String str = "my name is 007";

// ****************************短路操作*********************************

        // 并行流，顺序会乱
        str.chars().parallel().forEach(i -> System.out.print((char)i));
        System.out.println();

        // 并行流，使用forEachOrdered不会乱序
        str.chars().parallel().forEachOrdered(i -> System.out.print((char)i));
        System.out.println();

        // 收集到list collect:收集
        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list);

        // 使用reduce拼接字符串
        Optional<String> reduce = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "|" + s2);
        System.out.println(reduce.orElse(""));
        // 带初始化的reduce
        String reduce2 = Stream.of(str.split(" ")).reduce("",(s1, s2) -> s1 + "|" + s2);
        System.out.println(reduce2);
        // 计算所有单词的总长度
        Integer length = Stream.of(str.split(" ")).map(s -> s.length()).reduce(0, (s1, s2) -> s1 + s2);
        System.out.println("长度："+length);

        // 通过max获取长度最大的字符串
        Optional<String> max = Stream.of(str.split(" ")).max((x, y) -> x.length() - y.length());
        System.out.println(max.get());



// ****************************非短路操作*********************************
        OptionalInt first = new Random().ints().findFirst();
        System.out.println(first.getAsInt());

    }
}
