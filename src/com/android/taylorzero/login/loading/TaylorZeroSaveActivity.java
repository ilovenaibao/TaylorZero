package com.android.taylorzero.login.loading;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;
import android.app.Activity;
import android.os.Bundle;

public class TaylorZeroSaveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_save);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

}
