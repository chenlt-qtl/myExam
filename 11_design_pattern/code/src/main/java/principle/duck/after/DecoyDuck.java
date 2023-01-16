package principle.duck.after;

import principle.duck.after.behavior.FlyNoWay;
import principle.duck.after.behavior.QuackNoWay;

public class DecoyDuck extends Duck {


    public DecoyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new QuackNoWay();
    }

    @Override
    public void display() {
        System.out.println("诱鸭器");
    }
}
