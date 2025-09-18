package org.example.projectcdr;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LineQueue {
    private final BlockingQueue<LineItem> queue;

    public LineQueue(int size) {
        this.queue = new ArrayBlockingQueue<>(size);
    }

    public void put(LineItem t) throws InterruptedException {
        this.queue.put(t);
    }

    public LineItem take() throws InterruptedException {
        return this.queue.take();
    }
}
