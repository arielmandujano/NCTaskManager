package mx.tc.j2se.evaluation;

public class Circle {
    private int radius;

    /**
     * Default constructor for a Circle object, this initializes the radius of the circle to 1.
     */
    public Circle() {
        this.radius = 1;
    }

    /**
     * Constructor for a Circle object, this initializes the radius of the circle to the value of the parameter.
     * @param radius The radius of the circle.
     */
    public Circle(int radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("Error. The radius of the circle cannot be 0 or negative");
        }
        this.radius = radius;
    }

    /**
     * This method sets the value of the radius for an existing Circle object.
     * @param radius The new radius of the circle.
     */
    public void setRadius(int radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("Error. The radius of the circle cannot be negative or 0.");
        }
    }

    /**
     * This method returns the radius of the circle.
     * @return The radius of the circle.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * This method gets the area of the circle and returns it.
     * @return The area of the circle.
     */
    public double getArea() {
        return Math.PI * Math.pow((double) radius, 2.0);
    }

}
