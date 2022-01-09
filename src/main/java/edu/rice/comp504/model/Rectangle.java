package edu.rice.comp504.model;

import java.awt.*;
import java.util.Random;

/**
 * The Rectangle extend AShape.
 */
public class Rectangle extends AShape {
    private int high;
    private int width;
    private Random rand;
    /**
     * Constructor call.
     */
    public Rectangle(int high, int width, Point loc) {
        this.name = "rectangle";
        this.high = high;
        this.width = width;
        this.loc = loc;
        this.rand = new Random();
        this.color = availColors[this.rand.nextInt(availColors.length)];
    }

    @Override
    public void setAttrs() {
        this.color = availColors[rand.nextInt(availColors.length)];
    }

}
