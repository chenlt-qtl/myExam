package headFirst.principle.after;


import headFirst.principle.after.behavior.FlyWithWings;
import headFirst.principle.after.behavior.GaGaQuack;

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
