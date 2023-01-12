package factory.simpleFactory.coffee;

public class MeituanCoffeeStore {
    public void sellCoffee(String type){
        Coffee coffee = SimpleCoffeeFactory.createCoffee(type);
        System.out.println("美团外卖咖啡制作中");
        coffee.addMilk();
        coffee.addSurger();
        coffee.showName();
    }
}
