package headFirst.factory.simpleFactory;

public class ClamPizza extends Pizza {

    public ClamPizza() {
        name = "Clam Pizza";
    }

    @Override
    void cut() {
        System.out.println("Cutting the pizza into square slices");
    }
}
