package builder.drink;

import builder.IItem;
import builder.packing.Bottle;
import builder.packing.IPacking;

public abstract class Drink implements IItem {
    public IPacking getPacking() {
        return new Bottle();
    }
}
