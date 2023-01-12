package factory.factoryMethod;

public class MeituanCoffeeStore {

    private CoffeeFactory coffeeFactory;

    public void setCoffeeFactory(CoffeeFactory coffeeFactory){
        this.coffeeFactory = coffeeFactory;
    }
    public void sellCoffee(){
        Coffee coffee = coffeeFactory.createCoffee();
        System.out.println("美团外卖咖啡制作中");
        coffee.addMilk();
        coffee.addSurger();
        coffee.showName();
    }
}
