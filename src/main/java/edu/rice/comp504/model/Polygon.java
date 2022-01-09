package edu.rice.comp504.model;

import java.awt.*;
import java.util.Random;

/**
 * The Polygon extend AShape.
 */
public class Polygon extends AShape {
    private int radius;
    private int sides;
    private double rotateAngle;
    private Random rand;

    /**
     * Constructor call.
     */
    public Polygon(int radius, Point loc, int sides, double rotateAngle) {
        this.loc = loc;
        this.name = "polygon";
        this.radius = radius;
        this.sides = sides;
        this.rotateAngle = rotateAngle;
        this.rand = new Random();
        this.color = availColors[this.rand.nextInt(availColors.length)];
    }

    public int getSides() {
        return this.sides;
    }

    @Override
    public void setAttrs() {
        this.color = availColors[rand.nextInt(availColors.length)];
    }
}
