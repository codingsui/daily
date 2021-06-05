package cn.syl.swo.newa;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 *
 */
public class Solution59B {

    private Deque<Integer> deque;
    private Queue<Integer> queue;

    public Solution59B() {
        deque = new LinkedList<>();
        queue = new LinkedList<>();
    }

    public int max_value() {
        return deque.isEmpty() ? -1 : deque.peekFirst();
    }

    public void push_back(int value) {
        queue.offer(value);
        while (!deque.isEmpty() && deque.peekLast() < value){
            deque.pollLast();
        }
        deque.addLast(value);
    }

    public int pop_front() {
        if (queue.isEmpty()){
            return -1;
        }
        if (deque.peekFirst().equals(queue.peek())){
            deque.pollFirst();
        }
        return queue.poll();

    }

}
