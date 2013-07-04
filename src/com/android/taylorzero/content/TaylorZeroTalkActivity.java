package com.android.taylorzero.content;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

public class TaylorZeroTalkActivity extends Activity {

	public final static int MSG_HANDLER_SHOW_TALK = 0x0001;

	private Context mContext = null;
	MyLibScreenInfo scrInfo = null;
	public TextView person_name = null;
	public TextView person_talk = null;
	String talkContent = "在Android平台上，只有当一个View真正的layout到屏幕上之后，我们才可以通过()或者()得到它的宽高，如果有一个一行的TextView，我们需要在它layout到屏幕上之前就知道它大概要占多宽呢？可以借助下面的方法";
	String talkShow = "";
	Button start_talk = null;
	boolean isLoadingTalkShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_talk_dialog);
		mContext = this;
		scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
		RelativeLayout dialogLayout = (RelativeLayout) findViewById(R.id.talk_dialog_layout);
		if (null != dialogLayout) {
			ViewGroup.LayoutParams lp = dialogLayout.getLayoutParams();
			lp.height = scrInfo.scrHeight / 3;
			dialogLayout.setLayoutParams(lp);
			person_name = (TextView) findViewById(R.id.person_name_tv);
			Typeface font = Typeface.createFromAsset(mContext.getAssets(),
					"font/SIMKAI.TTF");
			person_name.setTypeface(font, Typeface.NORMAL);
			person_name.setGravity(Gravity.CENTER);
			person_name.setTextSize(28);
			person_talk = (TextView) findViewById(R.id.talk_dialog_tv);
			person_talk.setTypeface(font, Typeface.NORMAL);
			start_talk = (Button) findViewById(R.id.start_talk);
			start_talk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// showTalkContent(talkContent);
					if (!isLoadingTalkShow) {
						new LoadingTalkContentThread().start();
					}
				}
			});

		} else {
			Toast.makeText(mContext, "dialogLayout error!", Toast.LENGTH_SHORT)
					.show();
			finish();
			return;
		}
	}

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_HANDLER_SHOW_TALK:
				if (null != person_talk) {
					person_talk.setText(talkShow);
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	public class LoadingTalkContentThread extends Thread {

		public boolean isDestroy = false;

		public LoadingTalkContentThread() {

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			isLoadingTalkShow = true;
			char[] buffer = talkContent.toCharArray();
			int buffer_len = buffer.length;
			talkShow = "";
			for (int i = 0; i < buffer_len; i++) {
				try {
					sleep(100);
					talkShow += buffer[i];
					Message msg = new Message();
					msg.what = MSG_HANDLER_SHOW_TALK;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isLoadingTalkShow = false;

			super.run();
		}

	}

	public void showTalkContent(String buffer) {
		TextPaint paint = person_talk.getPaint();
		int txtWidth = (int) (paint.measureText(buffer) + 0.5);
		int txtHeight = (int) (person_talk.getTextSize() + 0.5);
		if (txtWidth > scrInfo.scrWidth - 30) {
			txtWidth = scrInfo.scrWidth - 30;
		}
		ViewGroup.LayoutParams talkTextViewParam = person_talk
				.getLayoutParams();
		talkTextViewParam.width = txtWidth;
		talkTextViewParam.height = ViewGroup.LayoutParams.FILL_PARENT;
		person_talk.setLayoutParams(talkTextViewParam);
		person_talk.setText(buffer);
		person_talk.setBackgroundColor(Color.RED);
		int person_talk_height = person_talk.getHeight();
		int maxLine = (int) (person_talk_height / txtHeight);
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
