package headFirst.principle.after;

import headFirst.principle.after.behavior.FlyNoWay;
import headFirst.principle.after.behavior.QuackNoWay;

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
