package headFirst.principle.after.behavior;

public class QuackNoWay implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("不会叫");
    }
}
