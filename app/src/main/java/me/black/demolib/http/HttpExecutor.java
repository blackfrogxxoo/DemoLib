package me.black.demolib.http;

public class HttpExecutor {
    private static final String WEATHER_BASE_URL = "https://api.seniverse.com";
    public static WeatherApi weatherApi() {
        return RetrofitManager.INSTANCE.create(WEATHER_BASE_URL, WeatherApi.class);
    }
    private static final String TEST_BASE_URL = "http://192.168.0.104:8891";
    public static TestApi testApi() {
        return RetrofitManager.INSTANCE.create(TEST_BASE_URL, TestApi.class);
    }
}
