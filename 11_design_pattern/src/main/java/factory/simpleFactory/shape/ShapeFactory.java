package factory.simpleFactory.shape;

public class ShapeFactory {
    public static IShape getShape(String type){
        if("Square".equalsIgnoreCase(type)){
            return new Square();
        }else if("Rectangle".equalsIgnoreCase(type)){
            return new Rectangle();
        }else {
            return null;
        }
    }
}
