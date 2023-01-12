package abstractFactory;

public class ColorFactory extends AbstractFactory {
    @Override
    public IColor getColor(String type){
        if("Red".equalsIgnoreCase(type)){
            return new Red();
        }else if("Blue".equalsIgnoreCase(type)){
            return new Blue();
        }else {
            return null;
        }
    }

    @Override
    public IShape getShape(String shape) {
        return null;
    }
}
