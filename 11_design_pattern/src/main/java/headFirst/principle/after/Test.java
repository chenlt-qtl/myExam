package headFirst.principle.after;


import headFirst.principle.after.behavior.FlyRocketPower;

public class Test {
    public static void main(String[] args) {

        Duck redHeadDuck = new RedHeadDuck();
        redHeadDuck.display();
        redHeadDuck.fly();
        redHeadDuck.quack();

        Duck rubberDuck = new RubberDuck();
        rubberDuck.display();
        rubberDuck.fly();
        rubberDuck.quack();

        rubberDuck.setFlyBehavior(new FlyRocketPower());
        rubberDuck.fly();

        DuckCall duckCall = new DuckCall();
        duckCall.quack();
    }
}
