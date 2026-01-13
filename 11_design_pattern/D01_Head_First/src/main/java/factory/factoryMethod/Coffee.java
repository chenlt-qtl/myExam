package factory.factoryMethod;

public abstract class Coffee {
    public abstract void showName();
    public void addSurger(){
        System.out.println("加糖");
    }

    public void addMilk(){
        System.out.println("加奶");
    }
}
