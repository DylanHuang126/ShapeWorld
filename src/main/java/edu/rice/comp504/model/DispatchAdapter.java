package edu.rice.comp504.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * The dispatch adapter holds all the shapes. It is responsible for communicating with the model
 * when there is a state change requiring an update to all the shapes.  The controller will
 * pass the JSON representation of the dispatch adapter to the view.
 */
public class DispatchAdapter {
    private static Point dims;
    private ArrayList<AShape> shapes;
    private Random rand = new Random();
    private final int aMAX = 40;
    private final int aMIN = 10;

    /**
     * Constructor call.
     */
    public DispatchAdapter() {
        shapes = new ArrayList<>();
    }

    /**
     * Get the canvas dimensions.
     * @return The canvas dimensions
     */
    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Set the canvas dimensions.
     * @param d The canvas width (x) and height (y).
     */
    public static void setCanvasDims(Point d) {
        dims = d;
    }

    /**
     * Call the update method on all the shapes.
     */
    public ArrayList<AShape> updateShapes() {
        ArrayList<AShape> updatedShapes = new ArrayList<>();
        AShape newestShape = shapes.get(shapes.size() - 1);
        String shapeName = newestShape.getName();

        updatedShapes.add(newestShape);

        for (Integer i = 0; i < shapes.size() - 1; i++) {
            AShape shape = shapes.get(i);

            if (shape.getName().equals(shapeName)) {
                if (shapeName.equals("polygon")) {
                    if (((Polygon) newestShape).getSides() == ((Polygon) shape).getSides()) {
                        shape.setAttrs();
                        updatedShapes.add(shape);
                    }
                } else {
                    shape.setAttrs();
                    updatedShapes.add(shape);
                }
            }
        }
        return updatedShapes;
    }

    /**
     *  Add a shape.
     * @param type  The type of shape to add
     * @return A ball
     */
    public AShape addShape(String type) {
        AShape shape;

        // hollow shape
        if (type.contains("_")) {
            String[] shapeNames = type.split("_");
            String base = shapeNames[0];
            String hollow = shapeNames[1];
            shape = addHollowShape(base, hollow);
        } else {
            switch (type) {
                case "rectangle":
                    shape = addRectangle();
                    break;
                case "rhombus":
                    shape = addRhombus();
                    break;
                case "hexagram":
                    shape = addHexagram();
                    break;
                case "circle":
                    shape = addCircle();
                    break;

                // default shape is polygon
                default:
                    int sides = Integer.valueOf(type.replace("polygon", ""));
                    shape = addPolygon(sides);
            }
        }
        shapes.add(shape);
        return shape;
    }

    /**
     * Remove all shapes.
     */
    public ArrayList<AShape> removeShapes() {
        shapes.clear();
        return shapes;
    }

    private AShape addCircle() {
        final int radius = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int x = rand.nextInt( dims.x - 2 * radius + 1) + radius;
        int y = rand.nextInt( dims.y - 2 * radius + 1) + radius;
        Point loc = new Point(x, y);

        AShape circle = new Circle(radius, loc);

        return circle;
    }

    private AShape addHexagram() {
        final int radius = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int x = rand.nextInt( dims.x - 2 * radius + 1) + radius;
        int y = rand.nextInt( dims.y - 2 * radius + 1) + radius;
        Point loc = new Point(x, y);
        final double rAngle1 = 0;
        final double rAngle2 = -Math.PI;
        final int sides = 3;

        AShape tri1 = new Polygon(radius, loc, sides, rAngle1);
        AShape tri2 = new Polygon(radius, loc, sides, rAngle2);
        AShape hexagram = new CompositeShape(tri1, tri2);

        return hexagram;
    }

    private AShape addRectangle() {
        int high = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int width = rand.nextInt(aMAX - aMIN + 1) + aMIN;

        int x = rand.nextInt(dims.x - width + 1);
        int y = rand.nextInt(dims.y - high + 1);
        Point loc = new Point(x, y);

        AShape shape = new Rectangle(high, width, loc);
        return shape;
    }

    private AShape addRhombus() {
        int high = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int width = rand.nextInt(aMAX - aMIN + 1) + aMIN;

        int x = rand.nextInt(dims.x - 2 * width + 1) + width;
        int y = rand.nextInt(dims.y - 2 * high + 1) + high;
        Point loc = new Point(x, y);

        AShape shape = new Rhombus(high, width, loc);

        return shape;
    }

    private AShape addPolygon(int sides) {
        final int radius = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int x = rand.nextInt( dims.x - 2 * radius + 1) + radius;
        int y = rand.nextInt( dims.y - 2 * radius + 1) + radius;
        Point loc = new Point(x, y);
        final double rotateAngle = 0;

        AShape polygon = new Polygon(radius, loc, sides, rotateAngle);

        return polygon;
    }

    private AShape addHollowShape(String base, String hollow) {

        final int radius = rand.nextInt(aMAX - aMIN + 1) + aMIN;
        int x = rand.nextInt( dims.x - 2 * radius + 1) + radius;
        int y = rand.nextInt( dims.y - 2 * radius + 1) + radius;
        Point loc = new Point(x, y);
        final double rAngle = 0;

        ArrayList<AShape> hollowShapes = new ArrayList<>();
        String[] tmp = {base, hollow};
        double[] size = {1, 0.5};
        for (int i = 0; i < tmp.length; i++) {
            AShape shape;
            switch (tmp[i]) {
                case "hexagram":
                    final int side = 3;
                    final double rAngle1 = 0;
                    final double rAngle2 = -Math.PI;
                    AShape tri1 = new Polygon((int)(radius * size[i]), loc, side, rAngle1);
                    AShape tri2 = new Polygon((int)(radius * size[i]), loc, side, rAngle2);
                    shape = new CompositeShape(tri1, tri2);
                    break;
                case "circle":
                    shape = new Circle((int)(radius * size[i]), loc);
                    break;
                default:
                    int sides = Integer.valueOf(tmp[i].replace("polygon", ""));
                    shape = new Polygon((int)(radius * size[i]), loc, sides, rAngle);
            }

            hollowShapes.add(shape);
        }

        AShape hollowShape = new CompositeShape(hollowShapes.get(0), hollowShapes.get(1));
        return hollowShape;
    }

}