package builder.burger;

import builder.IItem;
import builder.packing.IPacking;
import builder.packing.Wrapper;

public abstract class Burger implements IItem {

    public IPacking getPacking() {
        return new Wrapper();
    }
}
