package headFirst.decorator.starbuzz.decorator;

import headFirst.decorator.starbuzz.Beverage;
import headFirst.decorator.starbuzz.Condiment;


/**
 * 豆浆
 */
public class Soy extends Condiment {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+",Soy";
    }

    @Override
    public double cost() {
        return .15+beverage.cost();
    }
}
