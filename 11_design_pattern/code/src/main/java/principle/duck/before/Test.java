package principle.duck.before;


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
    }
}
