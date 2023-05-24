package headFirst.principle.before;

public abstract class Duck {

    public void fly(){
        System.out.println("用翅膀飞起来了");
    }

    public void quack(){
        System.out.println("嘎嘎嘎");
    }

    public void swim(){
        System.out.println("我在游泳");
    }
    public abstract void display();
}
