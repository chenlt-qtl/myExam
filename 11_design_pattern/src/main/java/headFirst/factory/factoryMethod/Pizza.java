package headFirst.factory.factoryMethod;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    String name;
    String dough;
    String sauce;
    List<String> toppings = new ArrayList<>();

    void prepare() {
        System.out.println("**************************");
        System.out.println("Preparing " + name);
        System.out.println("Tossing " + dough + " ...");
        System.out.println("Adding " + sauce + " ...");
        System.out.println("Adding toppings:");
        toppings.forEach(topping -> System.out.println(" " + topping));
    }

    void bake(){
        System.out.println("Bake for 25 minutes at 350");
    }

    void cut(){
        System.out.println("Cuttong the pizza into diagonal slices");
    }

    void box(){
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName(){
        return name;
    }
}
