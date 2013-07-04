package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

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
		mShowContentThread = new ShowContentThread();
		mShowContentThread.start();
	}

	float contentViewAlpha = 1;

	private class ShowContentThread extends Thread {

		public boolean isDestroy = false;
		public boolean isSuspend = false;

		protected void runPersonelLogic() {
			try {
				for (contentViewAlpha = 1; 0 <= contentViewAlpha;) {
					if (isSuspend) {
						while (true) {
							sleep(500);
							if (!isSuspend) {
								break;
							}
						}
					}
					sleep(50);
					contentViewAlpha -= 0.01;
					Message msg = new Message();
					msg.what = 0;
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
