package me.black.demolib.http;

import me.black.demolib.bean.WeatherBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {
    @GET("/v3/weather/now.json")
    Observable<WeatherBean> now(@Query("key") String key,
                                @Query("location") String location,
                                @Query("language") String language,
                                @Query("unit") String unit);
}
