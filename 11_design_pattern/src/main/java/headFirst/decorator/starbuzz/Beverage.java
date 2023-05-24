package headFirst.decorator.starbuzz;


/**
 * 饮料类
 */
public abstract class Beverage {

    protected String description = "unknow beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
