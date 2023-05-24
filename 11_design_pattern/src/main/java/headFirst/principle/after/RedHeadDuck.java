package headFirst.principle.after;


import headFirst.principle.after.behavior.FlyWithWings;
import headFirst.principle.after.behavior.GaGaQuack;

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
