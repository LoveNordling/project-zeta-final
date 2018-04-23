package org.primal;

import org.primal.graphics.UI;
import org.primal.map.Chunk;
import org.primal.map.Map;

public class Main {

    public static void main(String[] args) {
        Map map = new Map(1);
        UI ui = new UI(map, args);

        for (Chunk c : map.getChunks()) {
            Thread t = new Thread(c, "thread " + c.getId());
            t.start();
        }
    }
}