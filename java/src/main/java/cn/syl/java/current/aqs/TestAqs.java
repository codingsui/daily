package cn.syl.java.current.aqs;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestAqs implements Lock {

    public static void main(String[] args) {
        TestAqs lock = new TestAqs();
        final int[] num = {0};
        Lock l = new ReentrantLock();
        A a = new A();
        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                l.lock();
                for (int j = 1; j <= 10; j++) {

                    a.a= a.a +1;

                }
                l.unlock();
            }).start();
        }
        System.out.println(a.a);
    }
    private static class A{
        volatile int a;
    }

    private final Syc sync = new Syc();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private static class Syc extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            assert arg == 1;
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
        @Override
        protected boolean tryRelease(int arg) {
            assert arg == 1;
            if (getState() == 0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }
}
