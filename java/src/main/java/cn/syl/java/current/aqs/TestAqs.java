package cn.syl.java.current.aqs;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestAqs implements Lock {

    public static void main(String[] args) throws InterruptedException {
        TestAqs lock = new TestAqs();
        final int[] num = {0};
        A a = new A();
        List<Thread> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(()->{
                lock.lock();
                for (int j = 1; j <= 10; j++) {

                    a.a= a.a +1;

                }
                lock.unlock();

            });
            list.add(t);
        }
        for (Thread t:list) {
            t.start();
        }
        for (Thread t:list) {
            t.join();
        }
        System.out.println(a.a);

    }

    private Sync sync = new Sync();

    private static final class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            assert arg == 1;
            int status = getState();
            if (status == 0){
                if (compareAndSetState(0,arg)){
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }else if (Thread.currentThread() == getExclusiveOwnerThread()){
                int next = status + 1;
                if (next < 0){
                    throw new IllegalMonitorStateException();
                }
                setState(next);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            assert arg == 1;
            if (Thread.currentThread() != getExclusiveOwnerThread()){
                throw new IllegalMonitorStateException();
            }
            int status = getState() - arg;
            if (status == 0){
                setState(status);
                setExclusiveOwnerThread(null);
                return true;
            }
            setState(status);
            return false;
        }

        protected Condition newCondition(){
            return new ConditionObject();
        }


    }

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

    private static class A{
        int a;
    }

}
