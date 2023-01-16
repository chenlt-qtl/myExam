package principle.duck.after.behavior;

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不会飞");
    }
}
