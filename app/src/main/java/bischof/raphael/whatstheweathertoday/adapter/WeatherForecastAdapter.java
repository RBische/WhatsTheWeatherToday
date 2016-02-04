package bischof.raphael.whatstheweathertoday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import bischof.raphael.whatstheweathertoday.R;
import bischof.raphael.whatstheweathertoday.network.dateformat.BaseDateFormat;
import bischof.raphael.whatstheweathertoday.network.model.DetailForecastItem;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter used to show forecast items
 * Created by rbischof on 04/02/2016.
 */
public class WeatherForecastAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    private Context mContext;
    private ArrayList<DetailForecastItem> mItems;

    public WeatherForecastAdapter(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    /**
     * Add elements to the adapter
     * @param items Elements to add
     */
    public void addAll(Collection<DetailForecastItem> items){
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Clear all the elements of the adapter
     */
    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public DetailForecastItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getDateLong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int viewType = getItemViewType(position);
        DetailForecastItem currentItem = getItem(position);
        int layoutId = 0;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.adapter_weather_forecast_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.adapter_weather_forecast;
                break;
            }
        }
        if (convertView!=null){
            holder = (ViewHolder) convertView.getTag();
        }
        if (convertView==null || holder != null && holder.mViewType!=viewType){
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView,viewType);
            convertView.setTag(holder);
        }
        assert holder != null;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                holder.mIvIcon.setImageResource(getArtResourceForWeatherCondition(currentItem.getCurrentWeather().getWeatherId()));
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                holder.mIvIcon.setImageResource(getIconResourceForWeatherCondition(currentItem.getCurrentWeather().getWeatherId()));
                break;
            }
        }
        holder.mTvWeatherName.setText(currentItem.getCurrentWeather().getMain());
        holder.mTvHighTemperature.setText(String.format(mContext.getString(R.string.format_temperature), currentItem.getTemp().getMax()));
        holder.mTvLowTemperature.setText(String.format(mContext.getString(R.string.format_temperature), currentItem.getTemp().getMin()));
        holder.mTvDate.setText(new BaseDateFormat().format(currentItem.getDate()));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    static class ViewHolder{
        public int mViewType;
        @Bind(R.id.tvDate) TextView mTvDate;
        @Bind(R.id.tvHighTemperature) TextView mTvHighTemperature;
        @Bind(R.id.tvLowTemperature) TextView mTvLowTemperature;
        @Bind(R.id.tvWeatherName) TextView mTvWeatherName;
        @Bind(R.id.ivIcon) ImageView mIvIcon;

        public ViewHolder(View view, int viewType) {
            ButterKnife.bind(this, view);
            this.mViewType = viewType;
        }
    }

    /**
     * Helper method to provide the icon resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding icon. -1 if no relation is found.
     */
    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }

    /**
     * Helper method to provide the art resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding icon. -1 if no relation is found.
     */
    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }
}
