package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

public class TaylorZeroSoloDialogActivity extends Activity {
	private Context mContext = null;
	TextView contentView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_content);
		mContext = this;
		contentView = (TextView) findViewById(R.id.content_tv);
	}

	private class ShowContentThread extends Thread {
		String[] contentList = null;

		public ShowContentThread(String[] list) {
			contentList = list;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				sleep(3000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}

	}

	private Handler mHanler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				break;
			}
			super.handleMessage(msg);
		}

	};

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
