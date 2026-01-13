package headFirst.principle.after.behavior;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("我用翅膀飞");
    }
}
