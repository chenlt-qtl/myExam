package principle.duck.after;

import principle.duck.after.behavior.FlyWithWings;
import principle.duck.after.behavior.GaGaQuack;

public class RedHeadDuck extends Duck {

    public RedHeadDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new GaGaQuack();
    }
    @Override
    public void display() {
        System.out.println("红头鸭");
    }
}
