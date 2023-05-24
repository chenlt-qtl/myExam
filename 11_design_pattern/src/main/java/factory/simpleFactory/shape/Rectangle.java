package factory.simpleFactory.shape;

public class Rectangle implements IShape {
    @Override
    public void draw() {
        System.out.println("Draw Rectangle");
    }
}
