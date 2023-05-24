package headFirst.factory.factoryMethod;

public class ChicagoPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        if("cheese".equalsIgnoreCase(type)){
            return new ChicagoStyleCheesePizza();
        }

        return null;
    }
}
