package com.emcloud.ou.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
public class Azi {
    public void a() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("这是Java8之前的语法");
//            }
//        }).start();

//        new Thread( () -> System.out.println("这是Java8语法")).start();
//
//        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
//        double total = 0;
//        for (Integer cost : costBeforeTax) {
//            double price = cost + .12*cost;
//            total = total + price;
//        }
//        System.out.println("Total : " + total);
//
//        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
//        System.out.println("JAVA8 +Total : " + bill);
        System.out.println(Instant.now().plus(8, ChronoUnit.HOURS)) ;
        System.out.println(Instant.now()) ;

    }
}
