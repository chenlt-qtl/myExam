package builder;

import builder.packing.IPacking;

public interface IItem {
    String getName();
    IPacking getPacking();
    float getPrice();
}
