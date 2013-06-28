package com.android.taylorzero.opening;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroPlayBgMp3;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroOpening {
	private Context mContext = null;
	ImageView bt_openingMp4SeekEnd = null;
	TextView mp4_show_caption = null;
	LinearLayout mLayout_start_new_layout_animation = null;
	ImageView animation_imgView = null;
	/* 创建VideoView对象 */
	private VideoView videoView = null;
	private boolean isPlaying_pre = false;
	private int playingSeekTo = 0;
	private int mp4_len = 0;
	private int mp4_seek_end_bt_alpha = 90;
	// 100300 end pos

	TaylorZeroChosLogin chosLogin = null;
	private TaylorZeroPlayBgMp3 mPlayBgMp3 = null;

	public TaylorZeroOpening(Context context) {
		mContext = context;
	}

	// play opening mp4
	public void initializeMp4() {
		bt_openingMp4SeekEnd = (ImageView) ((Activity) mContext)
				.findViewById(R.id.seek_end_opening_mp4_imgbt);
		mp4_show_caption = (TextView) ((Activity) mContext)
				.findViewById(R.id.tv_video_caption);
		mLayout_start_new_layout_animation = (LinearLayout) ((Activity) mContext)
				.findViewById(R.id.layout_start_new_view);
		animation_imgView = (ImageView) ((Activity) mContext)
				.findViewById(R.id.layout_start_new_view_imgview);
		mLayout_start_new_layout_animation.setVisibility(View.GONE);
		mp4_show_caption.setVisibility(View.GONE);
		bt_openingMp4SeekEnd.setAlpha(mp4_seek_end_bt_alpha);
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
				.findViewById(R.id.opening_video_view);
		/* 设置路径 */
		videoView.setVideoPath(mp4_path);
		videoView.setOnCompletionListener(video_complete_listener);
		// MediaController video_controller = new MediaController(mContext);
		// videoView.setMediaController(video_controller);
		videoView.requestFocus();
		bt_openingMp4SeekEnd.setOnClickListener(bt_seek_end_listener);
		doPlayMp4File();
	}

	private class StartLoginUIThread extends Thread {
		private final static int StartLoginUI_DoPause = 0x0001;
		private final static int StartLoginUI_DoEnd = 0x0002;

		private int chosKind = 0;

		public StartLoginUIThread(int kind) {
			chosKind = kind;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int msg_arg1 = 0;
			switch (chosKind) {
			case StartLoginUI_DoPause:
				msg_arg1 = StartLoginUI_DoPause;
				break;
			case StartLoginUI_DoEnd:
				msg_arg1 = StartLoginUI_DoEnd;
				break;
			}

			Message msg = new Message();
			msg.what = TaylorZeroOpeningSetting.MSG_PLAY_OPENING_END;
			msg.arg1 = msg_arg1;
			mhandler.sendMessage(msg);

			super.run();
		}

	};

	private void loadLoginUI() {
		// start ui bg mp3
		mPlayBgMp3 = new TaylorZeroPlayBgMp3(mContext);
		mPlayBgMp3.playBackGroundMp3(TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroOpeningSetting.opening_mp3_bg_path, true);
		// setting ui
		RelativeLayout mLayout_play_video = (RelativeLayout) ((Activity) mContext)
				.findViewById(R.id.layout_play_video);
		LinearLayout mLayout_login = (LinearLayout) ((Activity) mContext)
				.findViewById(R.id.layout_login);
		if (videoView.isPlaying()) {
			doStopMp4File();
		}
		isPlaying_pre = false;
		mLayout_start_new_layout_animation.setVisibility(View.GONE);
		mLayout_play_video.setVisibility(View.GONE);
		mLayout_login.setVisibility(View.VISIBLE);
		chosLoginActivity();
	}

	private void chosLoginActivity() {
		chosLogin = new TaylorZeroChosLogin(mContext);
		chosLogin.initializeChosLogin();
	}

	public void setOnActivityResult(int requestCode, int resultCode, Intent data) {
		chosLogin.setOnActivityResult(requestCode, resultCode, data);
	}

	private class StartNewLayoutAnimation extends Thread {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = TaylorZeroOpeningSetting.MSG_START_NEW_LAYOUT_ANIMATION;
			mhandler.sendMessage(msg);
			super.run();
		}
	}

	private Handler mhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case TaylorZeroOpeningSetting.MSG_PLAY_OPENING_END:
				if (StartLoginUIThread.StartLoginUI_DoEnd == msg.arg1) {
					// start login UI
					loadLoginUI();
				} else if (StartLoginUIThread.StartLoginUI_DoPause == msg.arg1) {
					new StartNewLayoutAnimation().start();
				}
				break;
			case TaylorZeroOpeningSetting.MSG_START_NEW_LAYOUT_ANIMATION:
				int count = 0;
				for (count = 0; count < 256; count++) {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				loadLoginUI();
				// setStartNewLayoutAnimation(msg.arg1);
				break;
			}
			super.handleMessage(msg);
		}

	};

	OnClickListener bt_seek_end_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int end_pos = videoView.getDuration();
			v.setVisibility(View.GONE);
			mLayout_start_new_layout_animation.setVisibility(View.VISIBLE);
			// videoView.seekTo(end_pos);
			doPause();
			new StartLoginUIThread(StartLoginUIThread.StartLoginUI_DoPause)
					.start();
		}
	};

	OnCompletionListener video_complete_listener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			isPlaying_pre = false;
			new StartLoginUIThread(StartLoginUIThread.StartLoginUI_DoEnd)
					.start();
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
		if (null != mPlayBgMp3) {
			mPlayBgMp3.doStopMp3File();
		}
	}

	public void doPauseMp4File() {
		videoView.pause();
		playingSeekTo = videoView.getCurrentPosition();
		isPlaying_pre = false;
		if (null != mPlayBgMp3) {
			mPlayBgMp3.doPause();
		}
	}

	public void doResume() {
		if (isPlaying_pre) {
			videoView.seekTo(playingSeekTo);
			doPlayMp4File();
		}
		if (null != mPlayBgMp3) {
			mPlayBgMp3.doResume();
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
