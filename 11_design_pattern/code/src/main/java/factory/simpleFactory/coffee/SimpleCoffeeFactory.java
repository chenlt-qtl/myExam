package factory.simpleFactory.coffee;

public class SimpleCoffeeFactory {

    public static Coffee createCoffee(String type) {
        Coffee coffee;
        if ("american".equals(type)) {
            coffee = new AmericanCoffee();
        }else if("latte".equals(type)){
            coffee = new LatteCoffee();
        }else {
            throw new RuntimeException("您点的咖啡本店没有售卖");
        }
        return coffee;
    }
}
