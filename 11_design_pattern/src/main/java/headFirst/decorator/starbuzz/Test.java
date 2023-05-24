package headFirst.decorator.starbuzz;

import headFirst.decorator.starbuzz.coffee.Espresso;
import headFirst.decorator.starbuzz.coffee.HouseBlend;
import headFirst.decorator.starbuzz.decorator.Mocha;
import headFirst.decorator.starbuzz.decorator.Soy;
import headFirst.decorator.starbuzz.decorator.Whip;

public class Test {
    public static void main(String[] args) {
        //单点咖啡
        Beverage espresso1 = new Espresso();
        System.out.println(espresso1.getDescription());
        System.out.println(espresso1.cost());

        //双倍摩卡奶泡
        Beverage espresso2 = new Espresso();
        espresso2 = new Mocha(espresso2);
        espresso2 = new Mocha(espresso2);
        espresso2= new Whip(espresso2);
        System.out.println(espresso2.getDescription());
        System.out.println(espresso2.cost());

        //houseBlend 加豆浆 摩卡 奶泡
        Beverage houseBlend = new HouseBlend();
        houseBlend = new Mocha(houseBlend);
        houseBlend = new Soy(houseBlend);
        houseBlend = new Whip(houseBlend);
        System.out.println(houseBlend.getDescription());
        System.out.println(houseBlend.cost());
    }
}
