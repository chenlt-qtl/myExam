package headFirst.factory.simpleFactory;

public class Test {
    public static void main(String[] args) {
        PizzaStore pizzaStore = new PizzaStore(new SimplePizzaFactory());
        pizzaStore.orderPizza("cheese");

        pizzaStore.orderPizza("clam");
    }
}
