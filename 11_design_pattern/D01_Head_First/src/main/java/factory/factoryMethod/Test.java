package factory.factoryMethod;

public class Test {
    public static void main(String[] args) {
        CoffeeStore coffeeStore = new CoffeeStore();
        CoffeeFactory coffeeFactory = new AmericanCoffeeFactory();
        coffeeStore.setCoffeeFactory(coffeeFactory);
        coffeeStore.sellCoffee();
    }
}
