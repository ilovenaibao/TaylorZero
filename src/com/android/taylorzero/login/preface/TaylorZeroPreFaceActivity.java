package com.android.taylorzero.login.preface;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroPreFaceActivity extends Activity {
	private Context mContext = null;
	/* 创建VideoView对象 */
	private VideoView videoView = null;
	private boolean isPlaying_pre = false;
	private int playingSeekTo = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_play_video);
		mContext = this;
		initializeMp4();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != videoView) {
			doDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != videoView) {
			doPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		if (null != videoView) {
			doResume();
		}
		super.onResume();
	}

	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
	}

	// play opening mp4
	public void initializeMp4() {
		ImageView bt_openingMp4SeekEnd = (ImageView) ((Activity) mContext)
				.findViewById(R.id.seek_end_opening_mp4_imgbt);
		bt_openingMp4SeekEnd.setAlpha(0xA0A0A0A0);
		// String mp4_path = My_Static_Method_Lib.getResAbsolutePath(mContext,
		// TaylorZeroOpeningSetting.preface_mp4_path, false);
		String mp4_path = TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroOpeningSetting.preface_mp4_path;
		File tmpFile = new File(mp4_path);
		if (!tmpFile.exists()) {
			Toast.makeText(mContext, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			((Activity) mContext).finish();
			return;
		}
		videoView = (VideoView) ((Activity) mContext)
				.findViewById(R.id.opening_video_view);
		/* 设置路径 */
		videoView.setVideoPath(mp4_path);
		videoView.setOnCompletionListener(video_complete_listener);
		videoView.requestFocus();
		bt_openingMp4SeekEnd.setOnClickListener(bt_seek_end_listener);
		doPlayMp4File();
	}

	OnClickListener bt_seek_end_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			((Activity) mContext).finish();
		}
	};

	OnCompletionListener video_complete_listener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			((Activity) mContext).finish();
		}
	};

	public void doPlayMp4File() {
		videoView.start();
		isPlaying_pre = true;
	}

	public void doStopMp4File() {
		videoView.seekTo(0);
		videoView.pause();
		isPlaying_pre = false;
	}

	public void doPauseMp4File() {
		videoView.pause();
		playingSeekTo = videoView.getCurrentPosition();
		isPlaying_pre = false;
	}

	public void doResume() {
		if (isPlaying_pre) {
			videoView.seekTo(playingSeekTo);
			doPlayMp4File();
		}
	}

	public void doPause() {
		boolean tmpPlaying = isPlaying_pre;
		doPauseMp4File();
		if (tmpPlaying) {
			isPlaying_pre = tmpPlaying;
		}
	}

	public void doDestroy() {
		doStopMp4File();
	}
}
