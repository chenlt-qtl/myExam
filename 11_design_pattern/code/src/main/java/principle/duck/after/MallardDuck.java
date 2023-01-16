package principle.duck.after;

import principle.duck.after.behavior.FlyWithWings;
import principle.duck.after.behavior.GaGaQuack;

public class MallardDuck extends Duck {

    public MallardDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new GaGaQuack();
    }

    @Override
    public void display() {
        System.out.println("我是绿头鸭");
    }
}
