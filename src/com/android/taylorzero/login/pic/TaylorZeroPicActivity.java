package com.android.taylorzero.login.pic;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.mylib.xscan.FileTypeFilter;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroBmp;
import com.android.taylorzero.login.pic.TaylorZeroPicturesViewValues.BmpPosValue;
import com.android.taylorzero.setting.TaylorZeroPicActivitySetting;
import com.android.taylorzero.setting.TaylorZeroSetting;

public class TaylorZeroPicActivity extends Activity {
	private final int IMG_CONTROL_PRE = 0;
	private final int IMG_CONTROL_PLAY = 1;
	private final int IMG_CONTROL_NEXT = 2;

	private Context mContext = null;
	private TaylorZeroPicturesView pic_main_view = null;
	private ImageView[] imgController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_pic);
		mContext = this;
		imgController = new ImageView[3];
		imgController[IMG_CONTROL_PRE] = (ImageView) findViewById(R.id.imgview_show_pre);
		imgController[IMG_CONTROL_PLAY] = (ImageView) findViewById(R.id.imgview_show_play);
		imgController[IMG_CONTROL_NEXT] = (ImageView) findViewById(R.id.imgview_show_next);
		imgController[IMG_CONTROL_PRE].setOnClickListener(img_control_pre);
		imgController[IMG_CONTROL_PLAY].setOnClickListener(img_control_play);
		imgController[IMG_CONTROL_NEXT].setOnClickListener(img_control_next);
		pic_main_view = (TaylorZeroPicturesView) findViewById(R.id.activity_pic_view);
		MyLibScreenInfo scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
		String path = "/mnt/sdcard/Zero/res/Pic/2.png";
		// path = My_Static_Method_Lib.getResAbsolutePath(mContext,
		// TaylorZeroPicActivitySetting.save_pic_path, false);
		path = TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroPicActivitySetting.save_pic_path;
		File tmpFile = new File(path);
		if (!tmpFile.exists()) {
			Toast.makeText(mContext, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			((Activity) mContext).finish();
			return;
		}
		FileTypeFilter pngFileFilter = new FileTypeFilter() {
		};
		pngFileFilter.addType("zero_pic");
		File dir = new File(path);
		String[] pngFileListPath = dir.list(pngFileFilter);
		File[] pngFileList = dir.listFiles(pngFileFilter);
		Bitmap bmp = TaylorZeroBmp.loadBitmapAutoSize(path + "debug.zero_pic",
				scrInfo.scrWidth, scrInfo.scrHeight);
		if (null != pic_main_view && null != bmp) {
			pic_main_view.initializePicturesView(scrInfo.scrWidth,
					scrInfo.scrHeight);
			bmp = TaylorZeroBmp.BitmapRatioMatrix(bmp, scrInfo.scrWidth,
					scrInfo.scrHeight);
			pic_main_view.viewValue.setBackGroundBitmap(R.drawable.pic_ui_bg,
					scrInfo.scrWidth, scrInfo.scrHeight);
			pic_main_view.viewValue.setDrawBmpCacheBmp(bmp);
			pic_main_view.viewValue.setLoadingBitmapList(path, pngFileListPath);
			BmpPosValue bmpPos = pic_main_view.viewValue.setDrawBmpCacheBmpPos(
					this, bmp, TaylorZeroPicturesViewValues.SHOW_BMP_CENTER);
			pic_main_view.viewValue.setShowBmp(bmpPos.left, bmpPos.top, 0, 0,
					0, 0);
		} else {
			Toast.makeText(this, R.string.not_found_pic, Toast.LENGTH_SHORT);
			finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

	private OnClickListener img_control_pre = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener img_control_play = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener img_control_next = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
}
