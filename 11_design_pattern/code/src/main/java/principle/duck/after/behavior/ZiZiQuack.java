package principle.duck.after.behavior;

public class ZiZiQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("吱吱吱");
    }
}
