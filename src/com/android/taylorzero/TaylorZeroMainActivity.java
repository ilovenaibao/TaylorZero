package com.android.taylorzero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.opening.TaylorZeroOpening;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroMainActivity extends Activity {
	TaylorZeroOpening opening = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_opening);
		TaylorZeroSetting.Zero_Data_Real_Path = My_Static_Method_Lib
				.getResAbsolutePath(this, TaylorZeroSetting.Zero_Data_Path,
						false);
		if (null == TaylorZeroSetting.Zero_Data_Real_Path
				|| TaylorZeroSetting.Zero_Data_Real_Path.isEmpty()
				|| TaylorZeroSetting.Zero_Data_Real_Path.equals("")) {
			Toast.makeText(this, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		// start opening view
		startOpening();
	}

	private void startOpening() {
		opening = new TaylorZeroOpening(this);
		opening.initializeMp4();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		opening.setOnActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != opening) {
			opening.doDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != opening) {
			opening.doPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		if (null != opening) {
			opening.doResume();
		}
		super.onResume();
	}

	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
