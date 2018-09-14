package me.black.demolib;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import me.black.demolib.bean.BaseBean;
import me.black.demolib.bean.CheckMobileBean;
import me.black.demolib.http.HttpExecutor;
import me.black.library.glide.GlideApp;
import me.black.library.glide.GlideRequest;
import me.black.library.glide.SvgSoftwareLayerSetter;
import me.black.library.util.AndPermissionUtil;
import me.black.library.util.CallUtil;
import me.black.library.util.EvtBus;
import me.black.zxing.QRScannerActivity;
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
        Button btnYbya = findViewById(R.id.btn_ybya);
        btnYbya.setOnClickListener(v -> {
            HttpExecutor.testApi().checkMobile(new CheckMobileBean.Request("18523710221"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> {
                        Log.i(TAG, "onCreate: " + o.code);
                    }, Throwable::printStackTrace);
        });
        Button btnZxing = findViewById(R.id.btn_zxing);
        btnZxing.setOnClickListener(v -> {
            Intent it = new Intent(this, QRScannerActivity.class);
            startActivity(it);
        });
        Button btnChart = findViewById(R.id.btn_chart);
        btnChart.setOnClickListener(v -> {
            Intent it = new Intent(this, ChartActivity.class);
            startActivity(it);
        });
        Button btnTabDemo = findViewById(R.id.btn_tab_demo);
        btnTabDemo.setOnClickListener(v -> {
            Intent it = new Intent(this, TabDemoActivity.class);
            startActivity(it);
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
        CallUtil.call(this, "10001");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EvtBus.unregister(this);
    }
}
