package factory.simpleFactory.shape;

public class Test {
    public static void main(String[] args) {
        IShape shape1 = ShapeFactory.getShape("Square");
        shape1.draw();

        IShape shape2 = ShapeFactory.getShape("Rectangle");
        shape2.draw();
    }
}
