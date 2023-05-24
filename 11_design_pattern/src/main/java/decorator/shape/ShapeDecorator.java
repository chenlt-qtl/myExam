package decorator.shape;

public abstract class ShapeDecorator {

    protected IShape decoratedShape;

    public ShapeDecorator(IShape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    public abstract void draw();
}
