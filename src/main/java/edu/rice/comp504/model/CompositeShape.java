package edu.rice.comp504.model;

import java.util.ArrayList;

/**
 * The HollowShape extend AShape.
 */
public class CompositeShape extends AShape {
    private ArrayList<AShape> shapes;

    /**
     * Constructor call.
     */
    public CompositeShape(AShape s1, AShape s2) {
        this.name = s1.getName() + '_' + s2.getName();
        this.shapes = new ArrayList<>();
        addShape(s1);
        addShape(s2);
    }

    public void addShape(AShape s) {
        shapes.add(s);
    }

    public void removeShape(AShape s) {
        shapes.remove(s);
    }

    @Override
    public void setAttrs() {
        for (AShape shape: shapes) {
            shape.setAttrs();
        }
    }
}
