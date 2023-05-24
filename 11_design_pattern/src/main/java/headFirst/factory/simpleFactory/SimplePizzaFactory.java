package headFirst.factory.simpleFactory;

public class SimplePizzaFactory {

    public Pizza createPizza(String type){
        if("cheese".equalsIgnoreCase(type)){
            return new CheesePizza();
        }else if("clam".equalsIgnoreCase(type)){
            return new ClamPizza();
        }
        return null;
    }
}
