package org.example.projectcdr;


import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Productor implements Runnable {
    private final SharedFileReader sharedFileReader;
    private final LineQueue queue;

    private final AtomicLong sequence;

    public Productor(SharedFileReader sharedFileReader, LineQueue queue, AtomicLong sequence) {
        this.sharedFileReader = sharedFileReader;
        this.queue = queue;
        this.sequence = sequence;
    }

    @Override
    public void run() {
        String me = Thread.currentThread().getName();

        try {
            while (true) {
                String line = this.sharedFileReader.readLine();
                if (line == null) break;

                long sequence = this.sequence.incrementAndGet();
                this.queue.put(new LineItem(sequence, line, me, false));
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.printf("[%s] Error productor: %s%n", me, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
