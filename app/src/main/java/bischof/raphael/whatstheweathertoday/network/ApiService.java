package bischof.raphael.whatstheweathertoday.network;

import bischof.raphael.whatstheweathertoday.network.model.WeatherDailyForecast;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Description of the methods used on the Open Weather API
 * Created by rbischof on 03/02/2016.
 */
public interface ApiService {

    @POST("forecast/daily?cnt=10&mode=json&units=metric")
    Observable<WeatherDailyForecast> getDailyForecast(@Query("lat") Double latitude, @Query("lon") Double longitude, @Query("appid") String appId);
}
