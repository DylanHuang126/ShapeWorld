package edu.rice.comp504.model;


import java.awt.*;

/**
 * Abstract shape class that all concrete shape classes will implement.
 */
public abstract class AShape {
    protected Point loc;
    protected String color;
    protected String name;
    protected String[] availColors = {"red", "blue", "yellow", "green", "black", "purple", "orange", "gray", "brown"};

    /**
     * Get the shape name.
     * @return shape name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the shape color.
     * @return shape color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Set the shape color.
     */
    protected void setColor(String color) {
        this.color = color;
    }

    /**
     * Set the shape color attribute.  The shape will have a random color.
     */
    public abstract void setAttrs();

}



