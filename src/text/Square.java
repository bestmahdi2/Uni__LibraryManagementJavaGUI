package text;

public class Square {
    private double length;
    private double width;

    public Square() {
        length = 0;
        width = 0;
    }

    public Square(double l) {
        setDimension(l);
    }

    public void setDimension(double l) {
        length = l;
        width = l;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double area() {
        return length * width;
    }

    public double perimeter() {
        return (length) * 4;
    }

    public String toString() {
        return ("Length = " + length + ", Width = " + width);
    }
}