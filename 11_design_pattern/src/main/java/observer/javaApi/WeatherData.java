package observer.javaApi;


import lombok.Data;

import java.util.Observable;

@Data
public class WeatherData extends Observable {

    private float temp;
    private float humidity;
    private float pressure;


    public void setMeasurements(float temp, float humidity, float pressure){
        setTemp(temp);
        setHumidity(humidity);
        setPressure(pressure);
        setChanged();
        notifyObservers();
    }
}
