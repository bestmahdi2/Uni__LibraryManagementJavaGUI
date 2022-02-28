package text;

public class Box extends Square {
    private static int counter = 0;
    private double height;

    public Box() {
        super();
        counter++;
        height = 0;
    }

    public Box(double l) {
        super(l);
        counter++;
    }

    public void setDimension(double l) {
        super.setDimension(l);
        height = l;
    }

    public double getHeight() {
        return height;
    }

    public double area() {
        return 6 * (getLength() * getLength());
    }

    public double volume() {
        return super.area() * getLength();
    }

    public static int getBoxCounter(){
        return counter;
    }

    public String toString() {
        return (super.toString() + "; height = " + getLength());
    }
}