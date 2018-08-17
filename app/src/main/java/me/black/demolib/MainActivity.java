package me.black.demolib;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import me.black.demolib.bean.BaseBean;
import me.black.library.AndPermissionUtil;
import me.black.library.Bus;
import me.black.library.CallUtil;
import me.black.library.GlideApp;
import me.black.library.GlideRequest;
import me.black.library.GlideUtil;
import me.black.library.SvgSoftwareLayerSetter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bus.register(this);
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
            Bus.post(baseBean);
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
        Bus.unregister(this);
    }
}
