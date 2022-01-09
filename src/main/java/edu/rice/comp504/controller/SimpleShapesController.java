package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.model.DispatchAdapter;

import static spark.Spark.*;
import java.awt.Point;

/**
 * The SimpleShapesController is responsible for interfacing between the view and the model.  The model will determine
 * how shape objects are created.  The view is the browser.  The browser has a canvas that renders the shapes.
 * The controller interacts with the view by receiving REST get requests for various shapes.
 */
public class SimpleShapesController {
    private static DispatchAdapter d = new DispatchAdapter();

    /**
     *  Entry point into the program.
     * @param args  The arguments
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();

        get("/shape/polygon/:sides", (request, response) -> {
            String type = "polygon" + request.params(":sides");
            d.addShape(type);
            return gson.toJson(d.updateShapes());
        });

        get("/shape/hollow/:base/:hollow", (request, response) -> {
            String type = request.params(":base") + '_' + request.params(":hollow");
            d.addShape(type);
            return gson.toJson(d.updateShapes());
        });

        get("/shape/:shape", (request, response) -> {
            d.addShape(request.params(":shape"));
            return gson.toJson(d.updateShapes());
        });

        post("/canvas/dims", (request, response) -> {
            // TODO: retrieve canvas dimensions
            int height = Integer.valueOf(request.queryParams("height"));
            int width = Integer.valueOf(request.queryParams("width"));
            d.setCanvasDims(new Point(height, width));
            return gson.toJson("dimension received.");
        });

        post("/clear", (request, response) -> {
            d.removeShapes();
            return gson.toJson("clear");
        });

        redirect.get("/canvas", "/");
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
