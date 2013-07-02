package com.android.taylorzero.opening;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroPlayWidgetSound;
import com.android.taylorzero.login.pic.TaylorZeroPicActivity2;
import com.android.taylorzero.login.preface.TaylorZeroPreFaceActivity;
import com.android.taylorzero.login.start.TaylorZeroStartActivity;
import com.android.taylorzero.setting.TaylorZeroLoginSetting;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroChosLogin {
	private Context mContext = null;
	TextView tv_login_start = null;
	TextView tv_login_loading = null;
	TextView tv_login_pic = null;
	TextView tv_login_exit = null;
	TextView tv_login_preface = null;

	public TaylorZeroChosLogin(Context context) {
		mContext = context;
	}

	public void initializeChosLogin() {
		tv_login_start = (TextView) ((Activity) mContext)
				.findViewById(R.id.login_tv_start);
		tv_login_loading = (TextView) ((Activity) mContext)
				.findViewById(R.id.login_tv_loading);
		tv_login_pic = (TextView) ((Activity) mContext)
				.findViewById(R.id.login_tv_pic);
		tv_login_exit = (TextView) ((Activity) mContext)
				.findViewById(R.id.login_tv_exit);
		tv_login_preface = (TextView) ((Activity) mContext)
				.findViewById(R.id.login_tv_preface);

		tv_login_start.setOnClickListener(login_start_click_listener);
		tv_login_loading.setOnClickListener(login_loading_click_listener);
		tv_login_pic.setOnClickListener(login_pic_click_listener);
		tv_login_exit.setOnClickListener(login_exit_click_listener);
		tv_login_preface.setOnClickListener(login_preface_click_listener);
		// setting sound effects
		tv_login_start.setSoundEffectsEnabled(false);
		tv_login_loading.setSoundEffectsEnabled(false);
		tv_login_pic.setSoundEffectsEnabled(false);
		tv_login_exit.setSoundEffectsEnabled(false);
		tv_login_preface.setSoundEffectsEnabled(false);

		try {
			Typeface font = Typeface.createFromAsset(mContext.getAssets(),
					"font/cn_test.ttf");
			tv_login_start.setTypeface(font, Typeface.BOLD);
			tv_login_loading.setTypeface(font, Typeface.BOLD);
			tv_login_pic.setTypeface(font, Typeface.BOLD);
			tv_login_exit.setTypeface(font, Typeface.BOLD);
			tv_login_preface.setTypeface(font, Typeface.BOLD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOnActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_START:
			break;
		case TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_LOADING:
			break;
		case TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_PIC:
			break;
		case TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_EXIT:
			break;
		case TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_PREFACE:
			break;
		}
	}

	OnClickListener login_start_click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TaylorZeroPlayWidgetSound mWidgetSound = new TaylorZeroPlayWidgetSound(
					mContext);
			mWidgetSound
					.playWidgetSoundMp3(TaylorZeroSetting.Zero_Data_Real_Path
							+ TaylorZeroOpeningSetting.click_start_ui_activity_mp3_path);
			Intent intent = new Intent(mContext, TaylorZeroStartActivity.class);
			try {
				((Activity) mContext)
						.startActivityForResult(
								intent,
								TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_START);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	OnClickListener login_loading_click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TaylorZeroPlayWidgetSound mWidgetSound = new TaylorZeroPlayWidgetSound(
					mContext);
			mWidgetSound
					.playWidgetSoundMp3(TaylorZeroSetting.Zero_Data_Real_Path
							+ TaylorZeroOpeningSetting.click_start_ui_activity_mp3_path);
		}
	};

	OnClickListener login_pic_click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TaylorZeroPlayWidgetSound mWidgetSound = new TaylorZeroPlayWidgetSound(
					mContext);
			mWidgetSound
					.playWidgetSoundMp3(TaylorZeroSetting.Zero_Data_Real_Path
							+ TaylorZeroOpeningSetting.click_start_ui_activity_mp3_path);
			Intent intent = new Intent(mContext, TaylorZeroPicActivity2.class);
			try {
				((Activity) mContext)
						.startActivityForResult(
								intent,
								TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_PIC);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	OnClickListener login_exit_click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TaylorZeroPlayWidgetSound mWidgetSound = new TaylorZeroPlayWidgetSound(
					mContext);
			mWidgetSound
					.playWidgetSoundMp3(TaylorZeroSetting.Zero_Data_Real_Path
							+ TaylorZeroOpeningSetting.click_start_ui_activity_mp3_path);
			((Activity) mContext).finish();
		}
	};

	OnClickListener login_preface_click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TaylorZeroPlayWidgetSound mWidgetSound = new TaylorZeroPlayWidgetSound(
					mContext);
			mWidgetSound
					.playWidgetSoundMp3(TaylorZeroSetting.Zero_Data_Real_Path
							+ TaylorZeroOpeningSetting.click_start_ui_activity_mp3_path);
			Intent intent = new Intent(mContext,
					TaylorZeroPreFaceActivity.class);
			try {
				((Activity) mContext)
						.startActivityForResult(
								intent,
								TaylorZeroLoginSetting.START_ACTIVITY_REQUESTCODE_LOGIN_PREFACE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
