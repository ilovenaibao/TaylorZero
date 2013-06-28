package com.android.taylorzero;

import java.io.File;
import java.io.IOException;

import com.android.mylib.staticmethod.My_Static_Method_Lib;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class TaylorZeroPlayBgMp3 {
	private Context mContext = null;
	private MediaPlayer mediaplayer_mp3 = null;

	private boolean isPlaying_pre = false;
	private int start_seekTo = 0;
	private boolean isStartPlayReset = false;

	public TaylorZeroPlayBgMp3(Context context) {
		mContext = context;
	}

	// test play Mp3
	public void playBackGroundMp3(String file_path, boolean isResetFlag) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.taylorzero_activity_main, null);

		isPlaying_pre = false;
		isStartPlayReset = isResetFlag;

		String mp3_path = "/mnt/sdcard/Zero/test.mp3";
		// mp3_path = My_Static_Method_Lib.getResAbsolutePath(mContext,
		// file_path,
		// false);
		mp3_path = file_path;
		File tmpFile = new File(mp3_path);
		if (!tmpFile.exists()) {
			Toast.makeText(mContext, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			((Activity) mContext).finish();
			return;
		}
		Uri mp3_uri = Uri.fromFile(new File(mp3_path));
		mediaplayer_mp3 = MediaPlayer.create(mContext, mp3_uri);
		if (null == mediaplayer_mp3) {
			Toast.makeText(mContext, "Loading bg sound null!",
					Toast.LENGTH_SHORT).show();
			((Activity) mContext).finish();
			return;
		}
		mediaplayer_mp3.setOnCompletionListener(mp3_complete_listener);
		doPlayMp3File();
	}

	OnCompletionListener mp3_complete_listener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			doRecyclePlayMp3File();
			// Toast.makeText(mContext, "mp3 end!", Toast.LENGTH_SHORT).show();
		}
	};

	public int getPlayMp3Position() {
		return mediaplayer_mp3.getCurrentPosition();
	}

	public void setStarMp3Position(int startOffset) {
		start_seekTo = startOffset;
	}

	public void doPlayMp3File() {
		try {
			if (mediaplayer_mp3 != null) {
				mediaplayer_mp3.stop();
			}
			mediaplayer_mp3.prepare();
			if (isStartPlayReset) {
				mediaplayer_mp3.seekTo(start_seekTo);
			}
			mediaplayer_mp3.start();
			isPlaying_pre = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doRecyclePlayMp3File() {
		doPlayMp3File();
	}

	public void doStopMp3File() {
		if (null != mediaplayer_mp3) {
			boolean isPlaying = mediaplayer_mp3.isPlaying();
			if (isPlaying) {
				try {
					mediaplayer_mp3.seekTo(start_seekTo);
					mediaplayer_mp3.stop();
					isPlaying_pre = false;
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void doPauseMp3File() {
		if (null != mediaplayer_mp3) {
			boolean isPlaying = mediaplayer_mp3.isPlaying();
			if (isPlaying) {
				mediaplayer_mp3.pause();
				isPlaying_pre = false;
			}
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
