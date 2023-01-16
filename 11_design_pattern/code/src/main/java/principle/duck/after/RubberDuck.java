package principle.duck.after;

import principle.duck.after.behavior.FlyNoWay;
import principle.duck.after.behavior.ZiZiQuack;

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
