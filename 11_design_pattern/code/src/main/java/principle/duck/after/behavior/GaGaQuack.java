package principle.duck.after.behavior;

public class GaGaQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("嘎嘎嘎");
    }
}
