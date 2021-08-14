package cn.syl.leetcode.thread;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class Solution1195 {

    private int n;

    private int current = 1;

    private Semaphore s1 = new Semaphore(0);
    private Semaphore s2 = new Semaphore(0);
    private Semaphore s3 = new Semaphore(0);
    private Semaphore s4 = new Semaphore(0);


    public Solution1195(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true){
            s3.acquire();
            if (current > n){
                break;
            }
            printFizz.run();
            s1.release();
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true){
            s4.acquire();
            if (current > n){
                break;
            }
            printBuzz.run();
            s1.release();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true){
            s2.acquire();
            if (current > n){
                break;
            }
            printFizzBuzz.run();
            s1.release();
        }

    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (current <= n){
            if (current % 3 == 0 && current % 5 == 0){
                s2.release();
            }else if (current % 3 == 0){
                s3.release();
            }else if (current % 5 == 0){
                s4.release();
            }else {
                printNumber.accept(current);
                s1.release();
            }
            current++;
            s1.acquire();
        }
        s2.release();
        s3.release();
        s4.release();
    }

}

