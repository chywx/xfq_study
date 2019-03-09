package com.chendahai.collect;

import junit.textui.TestRunner;
import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CollectDemo {

    List<Colleague> list = null;

    @Before
    public void before(){
        list = Arrays.asList(
                new Colleague("陈海洋", 23, "男", "1202"),
                new Colleague("张希功", 25, "男", "1202"),
                new Colleague("李鑫", 24, "男", "1202"),
                new Colleague("赵峰", 33, "男", "1202"),
                new Colleague("陈家兹", 25, "女", "1202"),
                new Colleague("孙海波", 25, "女", "1202"),
                new Colleague("石鹏飞", 26, "男", "1202"),
                new Colleague("孙爱文", 27, "女", "1603"),
                new Colleague("曹慧", 25, "女", "1601"),
                new Colleague("何总", 31, "男", "1601"),
                new Colleague("谢总", 32, "男", "1601"),
                new Colleague("代巧梅", 25, "女", "1603"),
                new Colleague("易纪乐", 28, "女", "1202")
        );
    }

    public static void main(String[] args) {

    }


    // 获取所有同事的年龄列表
    // s -> s.getAge() --> Colleague::getAge 相比，不会多生成一个类似lambda的 $0这样的函数
    @Test
    public void  test1(){
        List<Integer> ageList = list.stream().map(Colleague::getAge).collect(Collectors.toList());
        System.out.println(ageList); // [23, 25, 24, 33, 25, 25, 26, 27, 25, 31, 32, 25, 28]
    }

    // 获取所有同事的年龄，去重
    @Test
    public void test2(){
        Set<Integer> ageSet = list.stream().map(Colleague::getAge).collect(Collectors.toSet());
        System.out.println(ageSet); // [32, 33, 23, 24, 25, 26, 27, 28, 31]
    }

    // 获取所有同事的年龄,自定义集合
    @Test
    public void test3(){
        TreeSet<Integer> ageTreeSet = list.stream().map(Colleague::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(ageTreeSet);// [23, 24, 25, 26, 27, 28, 31, 32, 33]
    }

    // 统计汇总信息  summarizing 汇总
    @Test
    public void test4(){
        IntSummaryStatistics ageSummarizingInt = list.stream().collect(Collectors.summarizingInt(Colleague::getAge));
        System.out.println(ageSummarizingInt);// IntSummaryStatistics{count=13, sum=349, min=23, average=26.846154, max=33}
    }

    // 分块 partitioning  verbose(冗长的，啰嗦的)
    @Test
    public void test5(){
        Map<Boolean,List<Colleague>> genders = list.stream().collect(Collectors.partitioningBy(s -> s.getGender().equals("男")));
        System.out.println("男女学生列表："+genders);
        System.out.println("使用工具类对map进行打印");
        MapUtils.verbosePrint(System.out,"男女学生列表",genders);
    }

    // 上面分块，可以进行分组操作
    @Test
    public void test6(){
        Map<String, List<Colleague>> grades = list.stream().collect(Collectors.groupingBy(Colleague::getGrade));
        MapUtils.verbosePrint(System.out,"同事办公室列表",grades);
    }

    // 分组，得到各个办公室同事的人数
    @Test
    public void test7(){
        Map<String, Long> gradeCount = list.stream().collect(Collectors.groupingBy(Colleague::getGrade, Collectors.counting()));
        MapUtils.verbosePrint(System.out,"办公室同事人数",gradeCount);
    }


}
