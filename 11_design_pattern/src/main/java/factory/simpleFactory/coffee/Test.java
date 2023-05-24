package factory.simpleFactory.coffee;

public class Test {
    public static void main(String[] args) {
        CoffeeStore coffeeStore = new CoffeeStore();
        coffeeStore.sellCoffee("latte");

        MeituanCoffeeStore meituanCoffeeStore = new MeituanCoffeeStore();
        meituanCoffeeStore.sellCoffee("american");
    }
}
