package com.kob.botrunningsystem.service.impl.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private final static ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition(); // 这里等会学一下
    private final Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId, String botCode, String input){
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll(); // 唤醒所有等待的线程
        } finally {
            lock.unlock();
        }

    }

    private void consume(Bot bot){ // 如果想运行其他语言的代码，可以在这里改
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000, bot);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if(bots.isEmpty()){
                try {
                    condition.await(); // 阻塞当前线程，等待被唤醒。await会释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot); // 编译代码耗时，可能会执行几秒钟。一定要在unlock之后执行
            }
        }
    }
}
