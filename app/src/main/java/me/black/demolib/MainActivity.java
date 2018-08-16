package me.black.demolib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.Arrays;

import me.black.library.AndPermissionUtil;
import me.black.library.CallUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermissionUtil.request(this, o -> {
            Log.i(TAG, "request permission success: " + Arrays.toString(o.toArray()));
        }, o -> {
            Log.e(TAG, "request permission failed: " + Arrays.toString(o.toArray()));
        });

        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(v -> {
            CallUtil.call(this, "10001");
        });
    }
}
