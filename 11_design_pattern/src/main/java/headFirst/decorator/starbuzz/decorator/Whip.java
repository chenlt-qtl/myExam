package headFirst.decorator.starbuzz.decorator;

import headFirst.decorator.starbuzz.Beverage;
import headFirst.decorator.starbuzz.Condiment;


/**
 * 奶泡
 */
public class Whip extends Condiment {
    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+",Whip";
    }

    @Override
    public double cost() {
        return .1+beverage.cost();
    }
}