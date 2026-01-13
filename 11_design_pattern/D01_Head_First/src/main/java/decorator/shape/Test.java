package decorator.shape;

import decorator.shape.Circle;
import decorator.shape.IShape;
import decorator.shape.RedShapeDecorator;
import decorator.shape.ShapeDecorator;

/**
 *
 */
public class Test {
    public static void main(String[] args) {
        //shape test
        IShape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(circle);
        circle.draw();
        redCircle.draw();


    }
}
