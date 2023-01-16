package principle.duck.after.behavior;

public class FlyRocketPower implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("火箭动力飞");
    }
}
