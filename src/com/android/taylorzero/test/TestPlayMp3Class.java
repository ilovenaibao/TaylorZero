package com.android.taylorzero.test;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.taylorzero.R;

public class TestPlayMp3Class {
	private Context mContext = null;
	private MediaPlayer mediaplayer_mp3 = null;
	private TextView tv_show_mp3_status = null;

	private Button bt_playMp3 = null;
	private Button bt_stopMp3 = null;
	private Button bt_pauseMp3 = null;

	private boolean isPlaying_pre = false;
	private int start_seekTo = 5000;

	public TestPlayMp3Class(Context context) {
		mContext = context;
	}

	// test play Mp3
	public void playMp3Test() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.taylorzero_activity_main, null);

		isPlaying_pre = false;
		bt_playMp3 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_play_mp3);
		bt_stopMp3 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_stop_mp3);
		bt_pauseMp3 = (Button) ((Activity) mContext)
				.findViewById(R.id.bt_pause_mp3);
		String mp3_path = "/mnt/sdcard/Zero/res/Sound/inter/chos_ui_activity_click_new.mp3";
		Uri mp3_uri = Uri.fromFile(new File(mp3_path));
		mediaplayer_mp3 = MediaPlayer.create(mContext, mp3_uri);
		tv_show_mp3_status = (TextView) ((Activity) mContext)
				.findViewById(R.id.tv_show_path);

		bt_playMp3.setOnClickListener(mp3_play_onClick_listener);
		bt_stopMp3.setOnClickListener(mp3_stop_onClick_listener);
		bt_pauseMp3.setOnClickListener(mp3_pause_onClick_listener);
		mediaplayer_mp3.setVolume(0, 0);
	}

	OnClickListener mp3_play_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doPlayMp3File();
		}
	};

	OnClickListener mp3_stop_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doStopMp3File();
		}
	};

	OnClickListener mp3_pause_onClick_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doPauseMp3File();
		}
	};

	public void doPlayMp3File() {
		try {
			if (mediaplayer_mp3 != null) {
				mediaplayer_mp3.stop();
			}
			mediaplayer_mp3.prepare();
			mediaplayer_mp3.start();
			tv_show_mp3_status.setText("playing mp3");
			isPlaying_pre = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doStopMp3File() {
		boolean isPlaying = mediaplayer_mp3.isPlaying();
		if (isPlaying) {
			try {
				mediaplayer_mp3.seekTo(start_seekTo);
				mediaplayer_mp3.stop();
				tv_show_mp3_status.setText("stop mp3");
				isPlaying_pre = false;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	public void doPauseMp3File() {
		boolean isPlaying = mediaplayer_mp3.isPlaying();
		if (isPlaying) {
			mediaplayer_mp3.pause();
			tv_show_mp3_status.setText("pause mp3");
			isPlaying_pre = false;
		}
	}

	public void doResume() {
		if (isPlaying_pre) {
			doPlayMp3File();
		}
	}

	public void doPause() {
		boolean tmpPlaying = isPlaying_pre;
		doPauseMp3File();
		if (tmpPlaying) {
			isPlaying_pre = tmpPlaying;
		}
	}

	public void doDestroy() {
		doStopMp3File();
		mediaplayer_mp3.release();
	}
}
