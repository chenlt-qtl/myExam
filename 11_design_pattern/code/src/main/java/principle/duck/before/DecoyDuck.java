package principle.duck.before;

public class DecoyDuck extends Duck {

    @Override
    public void fly() {
        System.out.println("不会飞");
    }

    @Override
    public void quack() {
        System.out.println("不会叫");
    }

    @Override
    public void display() {
        System.out.println("诱鸭器");
    }
}
