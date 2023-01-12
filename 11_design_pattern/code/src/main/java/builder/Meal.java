package builder;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private List<IItem> items = new ArrayList<IItem>();

    public void addItem(IItem item){
        items.add(item);
    }

    public float getCost(){
        Double cost = items.stream().mapToDouble(IItem::getPrice).reduce(0, (x, y) -> x += y);
        return cost.floatValue();
    }

    public void showItems(){
        items.forEach(item->{
            System.out.println("item:"+item.getName()+", packing:"+item.getPacking().getName()+", price:"+item.getPrice());
        });
        System.out.println("Total cost:"+this.getCost());
    }
}
