package factory.factoryMethod;

public class CoffeeStore {

    private CoffeeFactory coffeeFactory;

    public void setCoffeeFactory(CoffeeFactory coffeeFactory){
        this.coffeeFactory = coffeeFactory;
    }

    public void sellCoffee(){
        Coffee coffee = coffeeFactory.createCoffee();
        System.out.println("咖啡店咖啡制作中");
        coffee.addMilk();
        coffee.addSurger();
        coffee.showName();
    }
}
