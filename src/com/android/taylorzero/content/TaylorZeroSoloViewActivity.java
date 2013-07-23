package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroSoloViewActivity extends Activity {
	private Context mContext = null;
	ImageView contentView = null;
	ShowContentThread mShowContentThread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_solo_content);
		mContext = this;
		contentView = (ImageView) findViewById(R.id.solo_img_view);
		contentView.setVisibility(View.VISIBLE);
		contentView.setImageResource(R.drawable.be_generic_rgb_wo_60);
		// get sdk version
		TaylorZeroSetting.ANDROID_SDK_VERSION = My_Static_Method_Lib
				.getAndroidSDKVersion();
		mShowContentThread = new ShowContentThread();
		mShowContentThread.start();
	}

	float contentViewAlpha = 1;
	int contentViewAlphaInt = 255;

	private class ShowContentThread extends Thread {

		public boolean isDestroy = false;
		public boolean isSuspend = false;

		protected void runPersonelLogic() {
			try {
				contentViewAlpha = 1;
				contentViewAlphaInt = 255;
				while (true) {
					Message msg = new Message();
					msg.what = 0;
					if (isSuspend) {
						while (true) {
							sleep(500);
							if (!isSuspend) {
								break;
							}
						}
					}
					sleep(50);
					if (11 > TaylorZeroSetting.ANDROID_SDK_VERSION) {
						contentViewAlphaInt -= 3;
						if (0 > contentViewAlphaInt) {
							contentViewAlphaInt = 0;
							mHandler.sendMessage(msg);
							break;
						}
					} else {
						contentViewAlpha -= 0.01;
						if (0 > contentViewAlpha) {
							contentViewAlpha = 0;
							mHandler.sendMessage(msg);
							break;
						}
					}
					mHandler.sendMessage(msg);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			this.runPersonelLogic();
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				if (11 > TaylorZeroSetting.ANDROID_SDK_VERSION) {
					contentView.setAlpha(contentViewAlphaInt);
				} else {
					contentView.setAlpha(contentViewAlpha);
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != mShowContentThread) {
			mShowContentThread.isDestroy = true;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != mShowContentThread && !mShowContentThread.isSuspend) {
			mShowContentThread.isSuspend = true;
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		if (null != mShowContentThread && mShowContentThread.isSuspend) {
			contentViewAlpha = 1;
			mShowContentThread.isSuspend = false;
		}

		super.onResume();
	}
}
