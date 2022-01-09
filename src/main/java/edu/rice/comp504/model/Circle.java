package edu.rice.comp504.model;

import java.awt.Point;
import java.util.Random;

/**
 * The Circle extend AShape.
 */
public class Circle extends AShape {
    private int radius;
    private Random rand;
    /**
     * Constructor call.
     */
    public Circle(int radius, Point loc) {
        this.loc = loc;
        this.name = "circle";
        this.radius = radius;
        this.rand = new Random();
        this.color = availColors[this.rand.nextInt(availColors.length)];
    }


    @Override
    public void setAttrs() {
        this.color = availColors[this.rand.nextInt(availColors.length)];
    }

}