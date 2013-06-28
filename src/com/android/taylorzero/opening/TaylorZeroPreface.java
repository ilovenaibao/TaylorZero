package com.android.taylorzero.opening;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroPreface {
	private Context mContext = null;
	/* 创建VideoView对象 */
	private VideoView videoView = null;
	private boolean isPlaying_pre = false;
	private int playingSeekTo = 0;

	public TaylorZeroPreface(Context context) {
		mContext = context;
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
		videoView.requestFocus();
		doPlayMp4File();
	}

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
