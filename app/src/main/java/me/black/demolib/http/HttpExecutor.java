package me.black.demolib.http;

import me.black.library.http.RetrofitManager;

public class HttpExecutor {
    private static final String WEATHER_BASE_URL = "https://api.seniverse.com";
    public static WeatherApi weatherApi() {
        return RetrofitManager.INSTANCE.create(WEATHER_BASE_URL, WeatherApi.class);
    }
}
