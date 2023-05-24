package headFirst.principle.before;

public class RubberDuck extends Duck {

    @Override
    public void fly() {
        System.out.println("不会飞");
    }

    @Override
    public void quack() {
        System.out.println("吱吱吱");
    }

    @Override
    public void display() {
        System.out.println("橡皮鸭");
    }
}
