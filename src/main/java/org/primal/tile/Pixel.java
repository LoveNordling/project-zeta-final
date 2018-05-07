package org.primal.tile;

import java.awt.*;

public class Pixel {

    private Rectangle rectangle;
    private Color color;

    Pixel(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Color getColor() {
        return color;
    }
}
