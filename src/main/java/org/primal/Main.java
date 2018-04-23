package org.primal;

import org.primal.map.Chunk;
import org.primal.map.Map;

public class Main {

    public static void main(String[] args) {
        Map map = new Map(4);

        for (Chunk c : map.getChunks()) {
            Thread t = new Thread(c, "thread " + c.getId());
            t.start();
        }
    }
}