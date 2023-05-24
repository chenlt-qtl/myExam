package factory.abstractFactory;

/**
 * 优点：当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。
 *
 * 缺点：产品族扩展非常困难，要增加一个系列的某一产品，既要在抽象的 Creator 里加代码，又要在具体的里面加代码。
 *
 * 使用场景： 1、QQ 换皮肤，一整套一起换。
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("Shape");
        IShape square = shapeFactory.getShape("Square");
        square.draw();

        AbstractFactory colorFactory = FactoryProducer.getFactory("Color");
        IColor color = colorFactory.getColor("Red");
        color.fill();
    }
}
