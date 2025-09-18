package org.example.projectcdr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SharedFileReader implements AutoCloseable {
    private final BufferedReader br;

    public SharedFileReader(String path) throws IOException {
        this.br = new BufferedReader(new FileReader(path));
    }

    public synchronized String readLine() throws IOException {
        return this.br.readLine();
    }

    @Override
    public void close() throws IOException {
        this.br.close();
    }
}
