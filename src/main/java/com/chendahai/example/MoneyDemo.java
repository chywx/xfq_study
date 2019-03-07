package com.chendahai.example;


import java.text.DecimalFormat;
import java.util.function.Function;

interface IMoneyFormat{
    String format(int i);
}

class MyMoney{
    private final int money;

    public MyMoney(int money){
        this.money = money;
    }

    // 需要注释，要不报错
//    public void printMoney(IMoneyFormat moneyFormat){
//        System.out.println("我的存款：" + moneyFormat.format(money));
//    }

    public void printMoney(Function<Integer,String> function){
        System.out.println("我的存款：" + function.apply(money));
    }

}

public class MoneyDemo {
    public static void main(String[] args) {
        MyMoney myMoney = new MyMoney(899989999);
        myMoney.printMoney(i -> new DecimalFormat("#,###").format(i));
        Function<Integer,String> function = i -> new DecimalFormat("#,###").format(i);
        myMoney.printMoney(function.andThen(s -> s + "人民币"));
    }
}
