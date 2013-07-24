package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

/**
 * Created by BXC2011007 on 2013/7/24.
 */
public class TaylorZeroContentActivity extends Activity {
    private Context mContext = null;
    MyLibScreenInfo scrInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLibScreenSetting.SettingScreenShowTheme(this,
                MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
        MyLibScreenSetting.SettingScreenHorizontal(this);
        setContentView(R.layout.taylorzero_talk_dialog);
        mContext = this;
        scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MyLibScreenSetting.SettingScreenHorizontal(this);
        super.onResume();
    }
}
