package headFirst.decorator.starbuzz.coffee;

import headFirst.decorator.starbuzz.Beverage;

/**
 * 浓缩咖啡
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
