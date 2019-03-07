package com.chendahai.stream;

import java.util.stream.IntStream;

public class StreamDemo1 {
    public static void main(String[] args) {
        int [] arr = {1,2,4,3};
        int sum1 = 0;
        for (int i : arr) {
            sum1 += i;
        }
        System.out.println("1和为：" + sum1);

        // 求和属于终止操作
        // map属于中间操作，返回流的操作就是中间操作
        int sum2 = IntStream.of(arr).map(i -> i*2).sum();
        System.out.println("2和为：" + sum2);

        // 惰性求值,下面这一行不会打印出日志
        IntStream.of(arr).map(StreamDemo1::doubleNum);
        // 会进行打印日志操作
        int sum3 = IntStream.of(arr).map(StreamDemo1::doubleNum).sum();
        System.out.println("3和为：" + sum3);

    }

    public static int doubleNum(int i){
        System.out.println("执行了乘以2");
        return i * 2;
    }

}
