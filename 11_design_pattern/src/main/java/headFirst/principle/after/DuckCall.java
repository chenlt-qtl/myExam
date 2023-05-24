package headFirst.principle.after;


import headFirst.principle.after.behavior.GaGaQuack;
import headFirst.principle.after.behavior.QuackBehavior;

public class DuckCall {
    private QuackBehavior quackBehavior;

    public DuckCall() {
        quackBehavior = new GaGaQuack();
    }

    public void quack(){
        quackBehavior.quack();
    }
}
