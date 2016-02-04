package bischof.raphael.whatstheweathertoday.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Helper that returns Observable according to the preference key given
 * Created by rbischof on 04/02/2016.
 */
public class PreferencesManager {
    private final RxSharedPreferences mRxPreferences;

    public PreferencesManager(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mRxPreferences = RxSharedPreferences.create(preferences);
    }

    public Observable<Long> getLongSharedPreferencesObservable(String preferenceKey, long defaultValue){
        Preference<Long> preference = mRxPreferences.getLong(preferenceKey, defaultValue);
        return preference.asObservable().subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io());
    }
}
