## Simple Shapes Ext

> ### Info
- Heroku app url: https://lh49-hw2-shapes.herokuapp.com/ <br>

> ### Design Decision Report
#### Model
  Five shape classes extend AShape in my model design. For example, the Circle class has a field "radius" to store the radius of a circle. Likewise, the Rectangle and Rhombus class have "high" and "width" fields to hold the corresponding attribute. As for the Polygon class, I designed it to have "radius," "sides," and "rotateAngle" fields. Thus, my model supports the front-end to draw polygons with different sizes, numbers of sides, and even different arrangements with these three variables. The last "CompositeShape" class follows the composite design pattern, which handles shapes combined by two shapes. For instance, a hexagram is formed by two triangles with a rotation difference of 180 degrees. With the class, my model can handle any shape created by two basic shape components.

---
  
#### Controller
  In my SimpleShapesController, I use three "get" functions to handle all add shape requests. First of all, the controller handles basic shapes such as circles, rectangles, and rhombus through URL "/shape/:shape". Secondly, the URL "/shape/polygon/:sides" allows the controller to handle all polygons in one function(here, I only allow users to draw from triangle to octagon). Last but not least, to deal with hollow shape - a shape with another smaller shape add on it, I utilize the URL "/shape/hollow/:base/:hollow" to acquire base shape and add-on shape. All three "get" functions receive different parameters and then parse specific shape types, sides, etc., to pass into "addShape" function to consider various kinds of requests the front end may give.

---

#### View
1. I separate the hw1 design of creating shape into two-part. That is, users first use the HTML select component to select a shape type, and then they can click the add shape button to draw that kind of shape. The logic behind this is that when a user selects to draw a hollow shape, I allow them to choose the combinations of two shapes to form a hollow shape they want. So, separating creating shapes into select and add helped me realize this feature.
2. Similar to the design pattern in the controller, only three types of transmitting functions interact with the Java backend to reduce duplicate codes.
