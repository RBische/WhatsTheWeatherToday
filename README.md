# WhatsTheWeatherToday
Example app showing RxJava usage with Retrofit

Open Weather Map API Key is required.

In order for the WhatsTheWeatherToday app to function properly, an API key for openweathermap.org must be included in resources.

In order to make the app build, you must add a resources called "private_strings.xml" in your values folder.
This file must look like the following xml :

```
<resources>
    <string name="api_key"><UNIQUE_API_KEY></string>
</resources>
```
