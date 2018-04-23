package org.primal;

import org.primal.map.Chunk;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Chunk c = new Chunk(0, 0, i);
            Thread t = new Thread(c, "thread " + i);
            t.start();
        }
    }
}