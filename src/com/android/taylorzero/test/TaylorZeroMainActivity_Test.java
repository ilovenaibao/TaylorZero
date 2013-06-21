package com.android.taylorzero.test;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.taylorzero.R;
import com.android.taylorzero.R.layout;
import com.android.taylorzero.R.menu;

public class TaylorZeroMainActivity_Test extends Activity {
	private Context mContext = null;
	private TestPlayMp3Class mp3Test = null;
	private TestPlayMp4Class mp4Test = null;

	SoundPool soundPool = null;
	int explosionId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taylorzero_activity_main);
		mContext = this;
		// setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//
		// soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		// AssetManager assetManager = this.getAssets();
		// AssetFileDescriptor descriptor = null;
		// try {
		// descriptor = assetManager
		// .openFd("sound/chos_ui_activity_click.mp3");
		// explosionId = soundPool.load(descriptor, 1);
		// Button bt_test = (Button) findViewById(R.id.bt_play_mp3);
		// bt_test.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// soundPool.play(explosionId, 1.0f, 1.0f, 0, 0, 1);
		// }
		// });
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		mp3Test = new TestPlayMp3Class(this);
		mp3Test.playMp3Test();
		//
		mp4Test = new TestPlayMp4Class(this);
		mp4Test.playMp4Test();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != mp3Test) {
			mp3Test.doDestroy();
		}

		if (null != mp4Test) {
			mp4Test.doDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != mp3Test) {
			mp3Test.doPause();
		}

		if (null != mp4Test) {
			mp4Test.doPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (null != mp3Test) {
			mp3Test.doResume();
		}

		if (null != mp4Test) {
			mp4Test.doResume();
		}

		super.onResume();
	}

	@Override
	public void onContentChanged() {
		// TODO Auto-generated method stub
		super.onContentChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
