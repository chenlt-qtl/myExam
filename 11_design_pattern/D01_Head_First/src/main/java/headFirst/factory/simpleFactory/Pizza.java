package headFirst.factory.simpleFactory;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    String name;


    void prepare() {
        System.out.println("**************************");
        System.out.println("Preparing " + name);
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
