package com.unipi.stavrosvl7;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        int priority;
        long time = 0;
        System.out.println("Give the amount of philosophers");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        Philosopher[] philosophers = new Philosopher[x];
        Thread[] threads = new Thread[x];
        Object[] forks = new Object[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            priority = random.nextInt(10)+1;
            if (i == philosophers.length - 1) {
                // The last philosopher picks up the right fork first (deadlock)
                philosophers[i] = new Philosopher(rightFork, leftFork , priority);
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork , priority);
            }

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            threads[i] = t;
            t.setPriority(priority);
            t.start();
        }

        for (int i = 0; i < philosophers.length; i++) {

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Every philosopher ate for 20 seconds , programm finished");
    }
}
