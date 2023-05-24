package decorator.shape;

public class Rectangle implements IShape {
    @Override
    public void draw() {
        System.out.println("draw Rectangle");
    }
}
