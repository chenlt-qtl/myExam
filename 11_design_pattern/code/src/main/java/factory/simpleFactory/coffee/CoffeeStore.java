package factory.simpleFactory.coffee;

public class CoffeeStore {

    public void sellCoffee(String type){
        Coffee coffee = SimpleCoffeeFactory.createCoffee(type);
        System.out.println("咖啡店咖啡制作中");
        coffee.addMilk();
        coffee.addSurger();
        coffee.showName();
    }
}
