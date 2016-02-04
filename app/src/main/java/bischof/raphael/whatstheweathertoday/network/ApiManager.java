package bischof.raphael.whatstheweathertoday.network;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import bischof.raphael.whatstheweathertoday.network.model.WeatherDailyForecast;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Helper that creates an observable for each Retrofit request on the Open Weather API
 * Created by rbischof on 03/02/2016.
 */
public class ApiManager {
    public static final String URL = "http://api.openweathermap.org/data/2.5/";
    private ApiService service;

    public ApiManager() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong()*1000);
            }
        });

        // Asynchronous Call in Retrofit 2.0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public Observable<WeatherDailyForecast> getDailyForecast(Double latitude, Double longitude, String appId) {
        return service.getDailyForecast(latitude,longitude,appId);
    }
}
