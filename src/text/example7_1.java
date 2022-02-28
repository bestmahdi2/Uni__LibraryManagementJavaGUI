package text;

public class example7_1 {
    public static void main(String[] args) {
        Square mySquare1 = new Square();

        Square mySquare2 = new Square(8);

        System.out.println("mySquare1: " + mySquare1);

        System.out.println("Area of mySquare1: " + mySquare1.area());

        System.out.println("mySquare2: " + mySquare2);

        System.out.println("Area of mySquare2: " + mySquare2.area());

        Box myBox1 = new Box();

        Box myBox2 = new Box(10);

        System.out.println("myBox1: " + myBox1);

        System.out.println("Surface Area of myBox1: " + myBox1.area());

        System.out.println("Volume of myBox1: " + myBox1.volume());

        System.out.println("myBox2: " + myBox2);

        System.out.println("Surface Area of myBox2: " + myBox2.area());

        System.out.println("Volume of myBox2: " + myBox2.volume());

        System.out.println();

        System.out.println(Box.getBoxCounter() + " boxes were created till now !");
    }
}
