package org.example.projectcdr;

import java.util.ArrayDeque;
import java.util.Queue;

public class Buffer {
    private final Queue<CDRRegister> queueCDR = new ArrayDeque<>();

    public synchronized void put(CDRRegister register) {
        this.queueCDR.add(register);
    }

    public synchronized CDRRegister take() {
        return this.queueCDR.poll();
    }

}
