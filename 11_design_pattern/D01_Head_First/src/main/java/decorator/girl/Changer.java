package decorator.girl;

/**
 * 变形类
 */
public class Changer implements Morrigan{

    protected Morrigan morrigan;

    public Changer(Morrigan morrigan) {
        this.morrigan = morrigan;
    }

    @Override
    public void display() {
        morrigan.display();
    }
}
