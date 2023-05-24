package headFirst.principle.after;


import headFirst.principle.after.behavior.FlyNoWay;
import headFirst.principle.after.behavior.ZiZiQuack;

public class RubberDuck extends Duck {

    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new ZiZiQuack();
    }


    @Override
    public void display() {
        System.out.println("橡皮鸭");
    }
}
