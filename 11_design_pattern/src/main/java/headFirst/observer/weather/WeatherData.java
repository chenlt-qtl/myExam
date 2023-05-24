package headFirst.observer.weather;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherData implements Subject {

    private float temp;
    private float humidity;
    private float pressure;

    private List<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i > 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(getTemp(),getHumidity(),getPressure()));
    }

    public void setMeasurements(float temp, float humidity, float pressure){
        setTemp(temp);
        setHumidity(humidity);
        setPressure(pressure);
        notifyObservers();
    }
}
