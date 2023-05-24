package decorator.girl;

/**
 * 形象2
 */
public class Girl extends Changer{
    public Girl(Morrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        ((Original) super.morrigan).setImage("20230111144724.png");
        super.display();
    }
}
