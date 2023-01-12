package builder;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

/**
 * 建造者模式
 * 应用实例： 去肯德基，汉堡、可乐、薯条、炸鸡翅等是不变的，而其组合是经常变化的，生成出所谓的"套餐"。
 *
 * 优点： 1、建造者独立，易扩展。
 * 2、便于控制细节风险。
 *
 * 缺点： 1、产品必须有共同点，范围有限制。
 * 2、如内部变化复杂，会有很多的建造类。
 */
public class Test {

    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();
        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();

        Meal chickenMeal = mealBuilder.prepareChickenMeal();
        System.out.println("Chicken Meal");
        chickenMeal.showItems();
    }

}
