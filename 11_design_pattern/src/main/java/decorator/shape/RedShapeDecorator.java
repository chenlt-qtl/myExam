package decorator.shape;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(IShape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder();
    }

    private void setRedBorder(){
        System.out.println("Border color: Red");
    }
}
