package headFirst.factory.factoryMethod;

public class NYPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        if("cheese".equalsIgnoreCase(type)){
            return new NYStyleCheesePizza();
        }
        return null;
    }
}
