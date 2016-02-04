package bischof.raphael.whatstheweathertoday;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import bischof.raphael.whatstheweathertoday.adapter.WeatherForecastAdapter;
import bischof.raphael.whatstheweathertoday.network.ApiManager;
import bischof.raphael.whatstheweathertoday.network.model.WeatherDailyForecast;
import bischof.raphael.whatstheweathertoday.preferences.PreferencesManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Activity that shows the next 7 days forecast
 * Created by rbischof on 03/02/2016.
 */
public class MainActivity extends GPSApproximativeActivity {

    private Subscription mSubscription;
    @Bind(R.id.lvForecast)
    public ListView mLvForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLvForecast.setAdapter(new WeatherForecastAdapter(this));

        //Registers observables on latitude and longitude
        PreferencesManager manager = new PreferencesManager(this);
        Observable<Long> latObservable = manager.getLongSharedPreferencesObservable(getString(R.string.preference_key_latitude), 0l);
        Observable<Long> longObservable = manager.getLongSharedPreferencesObservable(getString(R.string.preference_key_longitude), 0l);

        //Combines these observables in a retrofit call with lat/long parameters
        Observable<Observable<WeatherDailyForecast>> dailyForecastObservable = Observable.combineLatest(latObservable, longObservable, new Func2<Long, Long, Observable<WeatherDailyForecast>>() {
            @Override
            public Observable<WeatherDailyForecast> call(Long latitude, Long longitude) {
                return new ApiManager().getDailyForecast(Double.longBitsToDouble(latitude), Double.longBitsToDouble(longitude), getString(R.string.api_key));
            }
        });

        //Flattens the Observable inception
        Observable<WeatherDailyForecast> flattenDailyForecastObservable = dailyForecastObservable.flatMap(new Func1<Observable<WeatherDailyForecast>, Observable<WeatherDailyForecast>>() {
            @Override
            public Observable<WeatherDailyForecast> call(Observable<WeatherDailyForecast> weatherDailyForecastObservable) {
                return weatherDailyForecastObservable;
            }
        });

        mSubscription = flattenDailyForecastObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeatherDailyForecast>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error", e);
                    }

                    @Override
                    public void onNext(WeatherDailyForecast forecast) {
                        WeatherForecastAdapter currentAdapter = ((WeatherForecastAdapter) mLvForecast.getAdapter());
                        currentAdapter.clear();
                        currentAdapter.addAll(forecast.getList());
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(getString(R.string.preference_key_latitude),Double.doubleToLongBits(location.getLatitude()));
        editor.putLong(getString(R.string.preference_key_longitude),Double.doubleToLongBits(location.getLongitude()));
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
