package com.android.taylorzero.content;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mylib.dbtypeconvert.MyDBTypeConvert;
import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

public class TaylorZeroTalkActivity extends Activity {
	private boolean DebugFlag = true;

	public final static int MSG_HANDLER_SHOW_TALK = 0x0001;

	private Context mContext = null;
	MyLibScreenInfo scrInfo = null;
	public TextView person_name = null;
	public TextView person_talk = null;
	String talkContent = "    1、这个很简单，如果你这些textview 在xml里面有添加的话，可以将它们存入int 1, []数组，然后在创建textview[] 数组，长度为id长度1。2、如果在xml中不存在id,那么动态增加，个数自定，同样创建textview[] 数组，长度即个数，自定义，可以根据需求使用for()循环创建，并添加的当前Activity中，设置布局手动添加。 \r\n3、根据上面2种情况，需要例子的话，再说。我一个activity里面有几十个textview，每个textview都需要动态的设置高度、宽度和添加点击事件，如果用findViewById获取控件，再设置宽高，几十个textview，会非常的麻烦，而且会有很多重复代码，怎么能一次遍历所有textview并设置宽高？或者说有其它的办法来设置？在Android平台上，只有当一个View真正的layout到屏幕上之后，我们才可以通过()或者()得到它的宽高，如果有一个一行的TextView，我们需要在它layout到屏幕上之前就知道它大概要占多宽呢？可以借助下面的方法";
	String talkShow = "";
	String oneLineShow = "";
	int otherShowCount = 0;
	boolean isLoadingTalkShow = false;
	int showContentSpeed = 10;
	TaylorZeroTalkContentSelectWindow talkContentSelectWindow = null;

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
			talkContentSelectWindow = new TaylorZeroTalkContentSelectWindow(
					this);
			ViewGroup.LayoutParams lp = dialogLayout.getLayoutParams();
			lp.height = scrInfo.scrHeight / 3;
			dialogLayout.setLayoutParams(lp);
			person_name = (TextView) findViewById(R.id.person_name_tv);
			Typeface font = Typeface.createFromAsset(mContext.getAssets(),
					"font/SIMKAI.TTF");
			person_name.setTypeface(font, Typeface.NORMAL);
			person_name.setGravity(Gravity.CENTER);
			person_name.setTextSize(20);
			person_talk = (TextView) findViewById(R.id.talk_dialog_tv);
			person_talk.setTypeface(font, Typeface.NORMAL);
            person_talk.setTextColor(Color.WHITE);
			Button start_talk = (Button) findViewById(R.id.start_talk);
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
			Button start_select_window = (Button) findViewById(R.id.start_select_window);
			start_select_window.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageView imgView = (ImageView) (((Activity) mContext)
							.findViewById(R.id.talk_dialog_img_view));
					ArrayList<TaylorZeroTalkSelectWindowsListOneData> listData = new ArrayList<TaylorZeroTalkSelectWindowsListOneData>();
					TaylorZeroTalkSelectWindowsListOneData oneData = new TaylorZeroTalkSelectWindowsListOneData();
					oneData.contentStr = "1、这个很简单，如果你这些textview 在xml里面有添加的话";
					listData.add(oneData);
					oneData = new TaylorZeroTalkSelectWindowsListOneData();
					oneData.contentStr = "2、如果在xml中不存在id,那么动态增加，个数自定";
					listData.add(oneData);
					oneData = new TaylorZeroTalkSelectWindowsListOneData();
					oneData.contentStr = "3、根据上面2种情况，需要例子的话，再说。我一个ac";
					listData.add(oneData);
					talkContentSelectWindow.setPopWindowListContent(listData);
					talkContentSelectWindow.setListView();
					talkContentSelectWindow.TalkContentWindow.showAtLocation(
							imgView, Gravity.CENTER, 0, 0);
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
			refreshShowContent();
			super.run();
		}

		public void refreshShowContent() {
			isLoadingTalkShow = true;
			talkContent = MyDBTypeConvert.ToDBC(talkContent);
			char[] buffer = talkContent.toCharArray();
			int buffer_len = buffer.length;
			talkShow = "";
			oneLineShow = "";
			TextPaint paint = person_talk.getPaint();
			int txtWidth = 0, txtHeight;
			txtWidth = 0;
			txtHeight = (int) (person_talk.getTextSize() + 0.5);
			int width = person_talk.getWidth();
			int height = person_talk.getHeight();
			int maxLineCount = height / txtHeight;
			int lineCount = 0;
			int realHeight = txtHeight;
			int i = 0;
			for (i = otherShowCount; i < buffer_len; i++) {
				try {
					sleep(showContentSpeed);
					// 計算之前text長度
					if (i < buffer_len - 1 && '\r' == buffer[i]
							&& '\n' == buffer[i + 1]) {
						oneLineShow = "";
						realHeight += txtHeight;
						lineCount++;
					}
					oneLineShow += buffer[i];
					talkShow += buffer[i];
					char[] tmpOneBuffer = new char[1];
					tmpOneBuffer[0] = buffer[i];
					if (i + 1 < buffer_len) {
						tmpOneBuffer[0] = buffer[i + 1];
					}
					String oneChar = new String(tmpOneBuffer);
					txtWidth = (int) (paint.measureText(oneLineShow)
							+ paint.measureText(oneChar) + 0.5);
					if (DebugFlag) {
						Log.e("paint.measureText = ",
								"char : " + oneChar + ", width = "
										+ paint.measureText(oneChar)
										+ ", realWidth = "
										+ paint.measureText(oneLineShow)
										+ ", totalWidth = " + txtWidth);
					}
					if (txtWidth > width) {
						talkShow += "\r\n";
						oneLineShow = "";
						realHeight += txtHeight;
						lineCount++;
					}
					if (realHeight > height) {
						otherShowCount = i + 1;
						break;
					}
					Message msg = new Message();
					msg.what = MSG_HANDLER_SHOW_TALK;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (i == buffer.length) {
				otherShowCount = 0;
			}
			isLoadingTalkShow = false;
		}

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
