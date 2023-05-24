package decorator.shape;

public class Circle implements IShape {
    @Override
    public void draw() {
        System.out.println("draw circle");
    }
}
