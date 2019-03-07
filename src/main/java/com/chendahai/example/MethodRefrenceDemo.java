package com.chendahai.example;

import java.util.function.*;

class Dog{
    private String name = "哮天犬";

    private int food = 10;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    public static void bark(Dog dog){
        System.out.println(dog + "叫了");
    }

    public int eat(Dog this,int sum){
        System.out.println("吃了"+sum+"斤狗粮");
        return food - sum;
    }


    @Override
    public String toString() {
        return "tostring:"+name;
    }
}


public class MethodRefrenceDemo {
    public static void main(String[] args) {
//        Consumer<String> consumer = i -> System.out.println(i);
        Consumer<String> consumer = System.out::println;
        consumer.accept("abc");

        // 静态方法的方法引用
        Consumer<Dog> consumer1 = Dog::bark;
        Dog dog = new Dog();
        consumer1.accept(dog);

        // 入参和出参类型相同可以使用UnaryOperator
        // 非静态方法的方法引用
//        Function<Integer,Integer> function = dog::eat;
//        UnaryOperator<Integer> function = dog::eat;
//        Integer apply = function.apply(3);
        IntUnaryOperator function = dog::eat;
        final int apply = function.applyAsInt(3);
        System.out.println("还剩下"+apply+"斤");

        // 可以通过biFunction直接通过对象::成员方法，而不需要对象实例
        BiFunction<Dog,Integer,Integer> eatFunction = Dog::eat;
        Integer apply1 = eatFunction.apply(new Dog(), 4);
        System.out.println(apply1);

        // 构造函数的方法引用
        Supplier<Dog> supplier = Dog::new;
        System.out.println("构造了一个对象："+supplier.get());

        // 带参数构造函数的方法引用
        Function<String,Dog> dogFunction = Dog::new;
        System.out.println(dogFunction.apply("旺财").toString());
    }
}
