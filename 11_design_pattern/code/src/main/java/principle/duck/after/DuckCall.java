package principle.duck.after;

import principle.duck.after.behavior.GaGaQuack;
import principle.duck.after.behavior.QuackBehavior;

public class DuckCall {
    private QuackBehavior quackBehavior;

    public DuckCall() {
        quackBehavior = new GaGaQuack();
    }

    public void quack(){
        quackBehavior.quack();
    }
}
