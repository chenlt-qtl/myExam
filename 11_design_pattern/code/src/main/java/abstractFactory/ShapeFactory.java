package abstractFactory;

public class ShapeFactory extends AbstractFactory{
    @Override
    public IColor getColor(String color) {
        return null;
    }

    @Override
    public IShape getShape(String type){
        if("Square".equalsIgnoreCase(type)){
            return new Square();
        }else if("Rectangle".equalsIgnoreCase(type)){
            return new Rectangle();
        }else {
            return null;
        }
    }
}
