package com.android.taylorzero.test;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.mylib.media.PlayMediaTime;
import com.android.mylib.media.PlayMediaTime.MediaTimer;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TestPlayMp4Class {
	private Context mContext = null;
	/* 创建VideoView对象 */
	private VideoView videoView = null;

	private TextView tv_show_mp4_status = null;

	private Button bt_playMp4 = null;
	private Button bt_stopMp4 = null;
	private Button bt_pauseMp4 = null;

	private boolean isPlaying_pre = false;

	public TestPlayMp4Class(Context context) {
		mContext = context;
	}

	// test play Mp4
	public void playMp4Test() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.taylorzero_activity_main, null);

		tv_show_mp4_status = (TextView) ((Activity) mContext)
				.findViewById(R.id.tv_show_path);
		bt_playMp4 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_playMp4);
		bt_stopMp4 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_stopMp4);
		bt_pauseMp4 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_pauseMp4);
		// String mp4_path = My_Static_Method_Lib.getResAbsolutePath(mContext,
		// TaylorZeroOpeningSetting.opening_mp4_path, false);
		String mp4_path = TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroOpeningSetting.opening_mp4_path;
		File tmpFile = new File(mp4_path);
		if (!tmpFile.exists()) {
			Toast.makeText(mContext, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			((Activity) mContext).finish();
			return;
		}
		videoView = (VideoView) ((Activity) mContext)
				.findViewById(R.id.mp4_view);
		/* 设置路径 */
		videoView.setVideoPath(mp4_path);
		videoView.requestFocus();

		bt_playMp4.setOnClickListener(mp4_play_onClick_listener);
		bt_stopMp4.setOnClickListener(mp4_stop_onClick_listener);
		bt_pauseMp4.setOnClickListener(mp4_pause_onClick_listener);
		videoView.setOnPreparedListener(mp4_prepare_listener);
		videoView.setOnCompletionListener(mp4_complete_listener);
		MediaController video_controller = new MediaController(mContext);
		videoView.setMediaController(video_controller);
	}

	OnCompletionListener mp4_complete_listener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			int a = mp.getCurrentPosition();
			MediaTimer timer = PlayMediaTime.getCurrentPlayTime(a);
			a = 1;
		}
	};

	OnPreparedListener mp4_prepare_listener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			// TODO Auto-generated method stub
			int a = 0;
			a = 1;
		}
	};

	OnClickListener mp4_play_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doPlayMp4File();
		}
	};

	OnClickListener mp4_stop_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doStopMp4File();
		}
	};

	OnClickListener mp4_pause_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doPauseMp4File();
		}
	};

	public void doPlayMp4File() {
		videoView.start();
		isPlaying_pre = true;
		tv_show_mp4_status.setText("play mp4");
	}

	public void doStopMp4File() {
		videoView.seekTo(0);
		videoView.pause();
		isPlaying_pre = false;
		tv_show_mp4_status.setText("stop mp4");
	}

	public void doPauseMp4File() {
		videoView.pause();
		isPlaying_pre = false;
		int a = videoView.getCurrentPosition();
		a = 1;
		tv_show_mp4_status.setText("pause mp4");
	}

	public void doResume() {
		if (isPlaying_pre) {
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
