package edu.rice.comp504.model;

import java.awt.*;
import java.util.Random;

/**
 * The Rhombus extend AShape.
 */
public class Rhombus extends AShape {
    private int high;
    private int width;
    private Random rand;
    /**
     * Constructor call.
     */
    public Rhombus(int high, int width, Point loc) {
        this.name = "rhombus";
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
