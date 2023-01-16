package decorator.starbuzz.decorator;

import decorator.starbuzz.Beverage;
import decorator.starbuzz.Condiment;


/**
 * 摩卡：巧克力酱、鲜奶油和牛奶
 */
public class Mocha extends Condiment {

    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription()+",Mocha";
    }

    @Override
    public double cost() {
        return .2+beverage.cost();
    }
}
