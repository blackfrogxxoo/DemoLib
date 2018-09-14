package me.black.demolib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

public class TabDemoActivity extends AppCompatActivity {

    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);
        TabHost mTabHost = findViewById(android.R.id.tabhost);
        mTabManager = new TabManager(this, mTabHost, R.id.realcontent);
        View tab1 = addTab(LayoutInflater.from(this), Constants.INDEX_1, R.drawable.ic_assessment_black_24dp, R.string.tab_1);
        mTabManager.addTab(mTabHost.newTabSpec(Constants.TAB_1).setIndicator(tab1), TestFragment1.class, null);
    }

    private View addTab(LayoutInflater from, int index1, int ic_assessment_black_24dp, int tab_1) {
        return null;
    }
}
