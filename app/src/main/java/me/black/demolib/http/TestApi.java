package me.black.demolib.http;

import me.black.demolib.bean.CheckMobileBean;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface TestApi {
    @POST("/appUser/checkMobile")
    Observable<CheckMobileBean> checkMobile(@Body CheckMobileBean.Request mobile);
}
