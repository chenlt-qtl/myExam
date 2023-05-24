package decorator.girl;

/**
 * 形象1
 */
public class Succubus extends Changer{
    public Succubus(Morrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        ((Original) super.morrigan).setImage("848840_food_512x512.png");
        super.display();
    }
}
