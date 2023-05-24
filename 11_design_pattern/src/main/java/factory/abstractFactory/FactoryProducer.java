package factory.abstractFactory;

public class FactoryProducer {
    public static AbstractFactory getFactory(String choice){
        if("Shape".equalsIgnoreCase(choice)){
            return new ShapeFactory();
        }else if("Color".equalsIgnoreCase(choice)){
            return new ColorFactory();
        }
        return null;
    }
}
