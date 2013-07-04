package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;

public class TaylorZeroSoloDialogActivity extends Activity {
	private Context mContext = null;
	TextView contentView = null;
	String[] contentList = null;
	ShowContentThread mShowContentThread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_solo_content);
		mContext = this;
		contentView = (TextView) findViewById(R.id.solo_dialog_tv);
		// contentView.setBackgroundColor(Color.RED);
		contentView.setTextSize(40);
		Typeface font = Typeface.createFromAsset(mContext.getAssets(),
				"font/SIMKAI.TTF");
		contentView.setTypeface(font, Typeface.NORMAL);
		contentView.setVisibility(View.VISIBLE);
		contentList = TaylorZeroOpeningSetting.preface_mp4_caption;
		mShowContentThread = new ShowContentThread();
		mShowContentThread.start();
	}

	float contentViewAlpha = 1;
	int showContentCount = 0;

	private class ShowContentThread extends Thread {

		public boolean isDestroy = false;
		public boolean isSuspend = false;

		protected void runPersonelLogic() {
			try {
				for (; !isDestroy && null != contentList
						&& showContentCount < contentList.length; showContentCount++) {
					if (isSuspend) {
						while (true) {
							sleep(500);
							if (!isSuspend) {
								break;
							}
						}
					}
					Message msg2 = new Message();
					msg2.what = 0;
					msg2.arg1 = showContentCount;
					mHandler.sendMessage(msg2);
					contentViewAlpha = 0;
					for (int j = 1000; 1 >= contentViewAlpha && 0 <= j; j--) {
						Message msg3 = new Message();
						msg3.what = 2;
						contentViewAlpha += 0.02;
						sleep(5);
						mHandler.sendMessage(msg3);
					}
					int oneCharTime = 300;
					sleep(contentList[showContentCount].length() * oneCharTime);
					for (int i = 1000; 0 <= contentViewAlpha && 0 <= i; i--) {
						Message msg = new Message();
						msg.what = 1;
						contentViewAlpha -= 0.01;
						sleep(10);
						mHandler.sendMessage(msg);
					}
					sleep(oneCharTime);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// Looper.prepare();
			this.runPersonelLogic();
			// Looper.loop();
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				contentView.setAlpha(contentViewAlpha);
				contentView.setText(contentList[msg.arg1]);
				break;
			case 1:
				contentView.setAlpha(contentViewAlpha);
				break;
			case 2:
				contentView.setAlpha(contentViewAlpha);
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

	int pauseShowContentCount = 0;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != mShowContentThread && !mShowContentThread.isSuspend) {
			pauseShowContentCount = showContentCount;
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
			showContentCount = pauseShowContentCount;
			pauseShowContentCount = showContentCount;
			mShowContentThread.isSuspend = false;
		}

		super.onResume();
	}
}