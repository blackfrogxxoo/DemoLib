package me.black.demolib;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import me.black.demolib.bean.BaseBean;
import me.black.demolib.http.HttpExecutor;
import me.black.library.glide.GlideApp;
import me.black.library.glide.GlideRequest;
import me.black.library.util.AndPermissionUtil;
import me.black.library.util.EvtBus;
import me.black.library.util.CallUtil;
import me.black.library.glide.SvgSoftwareLayerSetter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EvtBus.register(this);
        setContentView(R.layout.activity_main);

        AndPermissionUtil.request(this, o -> {
            Log.i(TAG, "request permission success: " + Arrays.toString(o.toArray()));
        }, o -> {
            Log.e(TAG, "request permission failed: " + Arrays.toString(o.toArray()));
        });

        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(v -> {
            BaseBean baseBean = new BaseBean();
            baseBean.phone = "10001";
            EvtBus.post(baseBean);
        });
        Button btnHttp = findViewById(R.id.btn_http);
        btnHttp.setOnClickListener(v -> {
            HttpExecutor.weatherApi()
                    .now("1e9zav6tbioroex3", "chengdu", "zh-Hans", "c")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        Toast.makeText(this, o.getResults().get(0).getNow().getTemperature(), Toast.LENGTH_LONG).show();
                    }, Throwable::printStackTrace);
        });

        ImageView imageView = findViewById(R.id.iv_test);

        GlideRequest<PictureDrawable> requestBuilder = GlideApp.with(this)
                .as(PictureDrawable.class)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter());

        Uri uri = Uri.parse("https://developer.android.com/_static/e408b0790b/images/android/lockup.svg");
        requestBuilder.load(uri).into(imageView);
//        GlideUtil.load(imageView, R.drawable.ic_launcher_foreground, "https://developer.android.com/_static/e408b0790b/images/android/lockup.svg");
    }

    @Subscribe
    public void testBus(BaseBean baseBean) {
        CallUtil.call(this, baseBean.phone);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EvtBus.unregister(this);
    }
}
