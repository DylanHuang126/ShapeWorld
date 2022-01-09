'use strict';

//app to draw polymorphic shapes on canvas
var app;

/**
 * Create an app that can draw shapes on a canvas. It also has a function to clear the canvas.
 * @param canvas  The canvas to draw shapes on.
 * @returns {{drawCircle: drawCircle, clear: clear}}, an object with functions to draw shapes and clear the canvas.
 */
function createApp(canvas) {
    var c = canvas.getContext("2d");

    let drawPolygon = function(x, y, radius, color, sides, rotateAngle) {
        c.fillStyle = color;
        c.beginPath();
        let angle = Math.PI * 2 / sides;
        c.translate(x, y);
        c.rotate(rotateAngle);
        c.moveTo(radius, 0);
        for (let i = 1; i < sides; i++) {
            c.lineTo(radius * Math.cos(angle * i), radius * Math.sin(angle * i))
        }
        c.closePath();
        c.fill();
        c.rotate(-rotateAngle);
        c.translate(-1 * x, -1 * y);
    }

    let drawCircle = function(x, y, radius, color) {
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);
        c.closePath();
        c.fill();
    };

    let drawRectangle = function (x, y, high, width, color) {
        c.fillStyle = color;
        c.fillRect(x, y, width, high)
    }

    let drawRhombus = function (x, y, high, width, color) {
        c.fillStyle = color;
        c.beginPath();
        c.lineTo(x + width, y);
        c.lineTo(x, y + high);
        c.lineTo(x - width, y);
        c.lineTo(x, y - high);
        c.closePath();
        c.fill();
    }

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    return {
        drawCircle: drawCircle,
        drawRectangle: drawRectangle,
        drawRhombus: drawRhombus,
        drawPolygon: drawPolygon,
        clear: clear,
        dims: {height: canvas.height, width: canvas.width}
    }
}

/**
 * When the window loads, get the app that can draw shapes on the canvas and setup the button click actions.
 */
window.onload = function() {
    app = createApp(document.querySelector("canvas"));
    clear();
    canvasDims();

    // monitor which shape type are selected.
    $("#shapeSelect").change(() => {
        if ($("#shapeSelect").val() === 'hollow') {
            $("#hollowBase").prop('disabled', false);
            $("#hollowTop").prop('disabled', false);
        } else{
            $("#hollowBase").prop('disabled', true);
            $("#hollowTop").prop('disabled', true);
        }
    })

    // add corresponding shape
    $("#btn-add").click(function() {
        let shape = $("#shapeSelect").val();

        if (shape.includes('polygon')) {
            let sides = shape.replace("polygon", "")
            createPolygon(sides);
        } else if ( shape === 'hollow'){
            let base = $("#hollowBase").val();
            let top = $("#hollowTop").val();
            createHollow(base, top);
        } else{
            createShape(shape);
        }
    });

    // clear canvas
    $("#btn-clear").click(clear);
};

/**
 * Pass along the canvas dimensions
 */
function canvasDims() {
    $.post("/canvas/dims", {height: app.dims.height, width: app.dims.width});
}

/**
 * Create a shape at a location on the canvas
 */
function createShape(shape) {
    switch (shape) {
        case('circle'):
            $.get("/shape/circle", function (data) {
                // console.log("data is " + JSON.stringify(data));
                data.forEach(c => app.drawCircle(c.loc.x, c.loc.y, c.radius, c.color));
            }, "json");
            break;
        case('rectangle'):
            $.get("/shape/rectangle", function (data) {
                data.forEach(t => app.drawRectangle(t.loc.x, t.loc.y, t.high, t.width, t.color));
            }, "json");
            break;
        case('rhombus'):
            $.get("/shape/rhombus", function (data) {
                data.forEach(t => app.drawRhombus(t.loc.x, t.loc.y, t.high, t.width, t.color));
            }, "json");
            break;
        case('hexagram'):
            $.get("/shape/hexagram", function (data) {
                data.forEach(s => s.shapes.forEach(c => app.drawPolygon(c.loc.x, c.loc.y, c.radius, c.color, c.sides, c.rotateAngle)));
            }, "json");
            break;
    }
}

/**
 * Create a polygon at a location on the canvas
 */
function createPolygon(sides) {
    let url = '/shape/polygon/' + sides;
    $.get(url, function (data) {
        data.forEach(c => app.drawPolygon(c.loc.x, c.loc.y, c.radius, c.color, c.sides, c.rotateAngle));
    }, "json");
}

/**
 * Create a hollow shape at a location on the canvas
 */
function createHollow(base, top) {
    let url = '/shape/hollow/' + base + '/' + top;
    $.get(url, function (data) {
        data.forEach(s => {
                s.shapes.forEach(c => {
                    switch (c.name) {
                        case 'polygon':
                            app.drawPolygon(c.loc.x, c.loc.y, c.radius, c.color, c.sides, c.rotateAngle);
                            break;
                        case 'polygon_polygon':
                            c.shapes.forEach(t => app.drawPolygon(t.loc.x, t.loc.y, t.radius, t.color, t.sides, t.rotateAngle));
                            break;
                        case 'circle':
                            app.drawCircle(c.loc.x, c.loc.y, c.radius, c.color);
                            break;
                    }
                })
            });
    }, "json");
}

/**
 * Clear the canvas
 */
function clear() {
    app.clear();
    $.post("/clear")
}