package bischof.raphael.whatstheweathertoday.network.model;

import java.util.ArrayList;
import java.util.Date;

public class DetailForecastItem {
    private Date dt;
    private Temperature temp;
    private ArrayList<Weather> weather;

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public long getDateLong(){
        return dt.getTime();
    }

    public Weather getCurrentWeather() {
        return weather.get(0);
    }

    public Temperature getTemp() {
        return temp;
    }

    public Date getDate() {
        return dt;
    }
}
