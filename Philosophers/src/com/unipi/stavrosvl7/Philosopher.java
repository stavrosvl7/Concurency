package com.unipi.stavrosvl7;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Philosopher implements Runnable {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy  hh:mm:ss aaa");
    private Object leftFork;
    private Object rightFork;
    private int priority;
    private long timeBefore;
    private long timeAfter;
    private long averageTime;
    private int counterTime;
    private long sum;


    public Philosopher(Object leftFork, Object rightFork, int priority) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.priority = priority;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // thinking
                print(formatter.format(new Date(System.currentTimeMillis())) + ": THINKING");
                if(sum<20000) {
                    timeBefore = System.currentTimeMillis();
                    counterTime++;
                    synchronized (leftFork) {
                        // hungry
                        print(formatter.format(new Date(System.currentTimeMillis())) + ": (HUNGRY) Picked up left fork and trying to aquire right fork");
                        synchronized (rightFork) {
                            // eating
                            timeAfter = System.currentTimeMillis();
                            if(sum<20000) {
                                sum += eat(sum,formatter.format(new Date(System.currentTimeMillis())) + ": Picked up right fork - EATING");
                                print(formatter.format(new Date(System.currentTimeMillis())) + ": Put down right fork");
                            }
                            averageTime += timeAfter - timeBefore;
                            if(sum>=20000){
                                System.out.println(Thread.currentThread().getName() + " " + formatter.format(new Date(System.currentTimeMillis())) + ": Waited approximately for " + averageTime/(1000*counterTime) + " seconds");
                            }

                        }

                        // Back to thinking
                        print(formatter.format(new Date(System.currentTimeMillis())) + ": Put down left fork. Back to THINKING");
                    }
                }
                else {
                    print(formatter.format(new Date(System.currentTimeMillis())) + ": Ate for 20 seconds and now will wait for others to finish");
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }

    private void print(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
    }

    private long eat(long sum,String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(Math.min(Math.abs(priority*1000),Math.abs(20000-sum)));
        return Math.min(Math.abs(priority*1000),Math.abs(20000-sum));
    }

}
