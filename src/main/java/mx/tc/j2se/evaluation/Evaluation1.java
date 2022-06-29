package mx.tc.j2se.evaluation;

public class Evaluation1 {

    public static void main(String[] args) {

        // Creating an invalid circle and catching the exception.

        try {
            Circle invalidCircle = new Circle(-1);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        // Creating an array of circles with 3 valid circles, then using the biggestCircle function
        // to get the index of the biggest circle in the array.

        Circle[] circles = {new Circle(), new Circle(10), new Circle(5)};

        int biggestCircleIndex = biggestCircle(circles);

        // Printing the radius ob the biggest circle in the array.

        System.out.println("The radius of the biggest circle is: " + circles[biggestCircleIndex].getRadius());

    }

    /**
     * This method returns the index of the biggest circle on a Circle object array. If the array its
     * empty, then returns -1.
     * @param circles An array of circle objects.
     * @return The index for the biggest circle in the array.
     */
    public static int biggestCircle(Circle[] circles) {
        int biggestCircle = -1;
        int index = -1;
        for(int count = 0 ; count < circles.length ; count++) {
            if(circles[count].getRadius() > biggestCircle) {
                biggestCircle = circles[count].getRadius();
                index = count;
            }
        }
        return index;
    }


}
