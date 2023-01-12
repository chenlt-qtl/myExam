package abstractFactory;

public abstract class AbstractFactory {
    public abstract IColor getColor(String color);
    public abstract IShape getShape(String shape);
}
