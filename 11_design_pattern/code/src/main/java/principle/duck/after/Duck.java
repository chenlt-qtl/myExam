package principle.duck.after;

import principle.duck.after.behavior.FlyBehavior;
import principle.duck.after.behavior.QuackBehavior;

public abstract class Duck {

    protected QuackBehavior quackBehavior;
    protected FlyBehavior flyBehavior;

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void fly(){
        flyBehavior.fly();
    }

    public void quack(){

        quackBehavior.quack();
    }

    public abstract void display();
}
