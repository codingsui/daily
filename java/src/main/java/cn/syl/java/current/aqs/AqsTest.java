package cn.syl.java.current.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AqsTest {
    public static void main(String[] args) {
        MyMutex m = new MyMutex();
        final int[] sum = {0};
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    m.lock();
                    for (int j = 0; j < 1000; j++) {
                        sum[0]++;
                    }
                    m.unlock();
                }
            }.start();
        }
        System.out.println(sum[0]);
    }


    static class MyMutex implements Lock{

        private final MySync sync = new MySync();


        class MySync extends AbstractQueuedSynchronizer{
            @Override
            protected boolean tryAcquire(int arg) {
                assert arg == 1;
                int status = getState();
                if (status == 0){
                    if (compareAndSetState(0,arg)){
                        setExclusiveOwnerThread(Thread.currentThread());
                        return true;
                    }
                }else if (getExclusiveOwnerThread() == Thread.currentThread()){
                    if (status < 0){
                        throw new IllegalMonitorStateException();
                    }
                    setState(status + 1);
                }
                return false;
            }

            @Override
            protected boolean tryRelease(int arg) {
                if (getExclusiveOwnerThread() != Thread.currentThread()){
                    throw new IllegalMonitorStateException();
                }
                int state = getState() - arg;
                if (state == 0){
                    setState(state);
                    setExclusiveOwnerThread(null);
                    return true;
                }
                setState(state);
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            @Override
            protected boolean isHeldExclusively() {
                return getState() == 1;
            }

            Condition newCondition(){
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
    }
}
