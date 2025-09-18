package org.example.projectcdr;


public class Consumer implements Runnable {
    private final LineQueue queue;


    public Consumer(LineQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String me = Thread.currentThread().getName();

        try {
            while (true) {
                LineItem it = this.queue.take();

                if (it.poison) {
                    System.out.printf("[%s] finalizo%n", me);
                    break;
                }

                System.out.printf("[%s] consumio seq=%d de %s :: %s%n", me, it.seq, it.producer, it.text);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
