package headFirst.observer.weather;

public class StatisticsDisplay implements Observer, DisplayElement {

    private float temp;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Avg/Max/Min temperature = " + temp + "/"+temp+"/"+temp);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temp = temp;
        display();
    }
}
