package com.android.taylorzero.login.pic;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.mylib.graphic.MyGraphic;
import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.mylib.xscan.FileTypeFilter;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroBmp;
import com.android.taylorzero.TaylorZeroPlayBgMp3;
import com.android.taylorzero.login.pic.TaylorZeroPicturesViewValues.BmpPosValue;
import com.android.taylorzero.login.pic.popdirlist.DSLVFragmentClicks;
import com.android.taylorzero.login.pic.popdirlist.DragInitModeDialog;
import com.android.taylorzero.login.pic.popdirlist.EnablesDialog;
import com.android.taylorzero.login.pic.popdirlist.RemoveModeDialog;
import com.android.taylorzero.login.pic.popdirlist.TaylorZeroDirListPopWindow;
import com.android.taylorzero.setting.TaylorZeroOpeningSetting;
import com.android.taylorzero.setting.TaylorZeroPicActivitySetting;
import com.mobeta.android.dslv.DragSortController;

public class TaylorZeroPicActivity2 extends FragmentActivity implements
		RemoveModeDialog.RemoveOkListener, DragInitModeDialog.DragOkListener,
		EnablesDialog.EnabledOkListener {

	public final static int MSG_HANDLER_UPDATE_DIRLIST_PATH = 0x0002;
	public final static int MSG_HANDLER_REFRESH_SHOW_VIEW = 0x0003;
	public final static String BUNDLE_KEY_DIR_LIST_PATH = "BUNDLE_KEY_DIR_LIST_PATH";

	private int mNumHeaders = 0;
	private int mNumFooters = 0;

	private int mDragStartMode = DragSortController.ON_DRAG;
	private boolean mRemoveEnabled = true;
	private int mRemoveMode = DragSortController.FLING_REMOVE;
	private boolean mSortEnabled = true;
	private boolean mDragEnabled = true;

	private String mTag = "dslvTag";

	public static Context mContext = null;
	private RelativeLayout main_layout = null;
	private String picPath = "";
	private String defaultPicPath = "";
	TaylorZeroPicturesView mOneMirroView = null;
	MyLibScreenInfo scrInfo = null;
	View pre_border_select_view = null;
	LinearLayout list_layout = null;
	private TaylorZeroPlayBgMp3 mPlayBgMp3 = null;
	int now_volume_pos = 0;
	boolean isOpenVolume = true;
	boolean isShowDirListLayout = false;
	private int popDirListWindowX, popDirListWindowY;
	private ImageAdapter galleryImageAdapter = null;
	private Gallery gallery = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_pic2);
		mContext = this;
		main_layout = (RelativeLayout) findViewById(R.id.layout1);
		gallery = (Gallery) findViewById(R.id.mygallery);
		scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
		scrInfo.scrHeight -= 120;
		// scrInfo.scrWidth -= 400;
		if (savedInstanceState == null) {
			popDirListWindowX = scrInfo.scrWidth / 4;
			popDirListWindowY = scrInfo.scrHeight;
			list_layout = (LinearLayout) findViewById(R.id.list_layout);
			if (null != list_layout) {
				ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) list_layout
						.getLayoutParams();
				lp.width = popDirListWindowX;
				lp.height = popDirListWindowY;
				list_layout.setLayoutParams(lp);
				list_layout.setVisibility(View.GONE);
			}
			getSupportFragmentManager().beginTransaction()
					.add(R.id.test_bed, getNewDslvFragment(this), mTag)
					.commit();
		}
		defaultPicPath = My_Static_Method_Lib.getResAbsolutePath(mContext,
				TaylorZeroPicActivitySetting.save_pic_path, false);
		picPath = defaultPicPath;
		imageDataList = getImageList(picPath);
		mOneMirroView = new TaylorZeroPicturesView(mContext);
		if (null != mOneMirroView) {
			mOneMirroView.initializePicturesView(scrInfo.scrWidth,
					scrInfo.scrHeight);
			mOneMirroView.viewValue.setBackGroundBitmap(R.drawable.pic_ui_bg,
					scrInfo.scrWidth, scrInfo.scrHeight);
			RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			rp.addRule(RelativeLayout.ABOVE, R.id.mygallery);
			rp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			int childCount = main_layout.getChildCount();
			main_layout.addView(mOneMirroView, 1);
			mOneMirroView.setLayoutParams(rp);
		}
		galleryImageAdapter = new ImageAdapter(TaylorZeroPicActivity2.this);
		gallery.setAdapter(galleryImageAdapter);
		gallery.setSpacing(10);
		gallery.setSelection(0);
		gallery.setOnItemLongClickListener(longClickListener);
		gallery.setOnItemSelectedListener(selectListener);

		mPlayBgMp3 = new TaylorZeroPlayBgMp3(this);
		mPlayBgMp3.playBackGroundMp3(
				TaylorZeroOpeningSetting.opening_mp3_bg_path, true);

		ImageView controllerVolume = (ImageView) findViewById(R.id.volume_imgview);
		isOpenVolume = true;
		controllerVolume.setImageResource(R.drawable.open_volume);
		controllerVolume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isOpenVolume) {
					now_volume_pos = mPlayBgMp3.getPlayMp3Position();
					mPlayBgMp3.setStarMp3Position(now_volume_pos);
					mPlayBgMp3.doPause();
					((ImageView) v).setImageResource(R.drawable.close_volume);
					isOpenVolume = false;
				} else {
					mPlayBgMp3.doPlayMp3File();
					((ImageView) v).setImageResource(R.drawable.open_volume);
					isOpenVolume = true;
				}
			}
		});

		ImageView test_dir_list_view = (ImageView) findViewById(R.id.dir_list_imgbt);
		// dir_list_window = new TaylorZeroDirListPopWindow(this);
		test_dir_list_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (null != list_layout) {
					if (isShowDirListLayout) {
						list_layout.setVisibility(View.GONE);
						list_layout.startAnimation(setMyDefineAnimation(2));
						list_layout
								.setLayoutAnimation(getAnimationController(2));
						((ImageView) v)
								.setImageResource(R.drawable.close_folders);
						isShowDirListLayout = false;
					} else {
						list_layout.setVisibility(View.VISIBLE);
						list_layout.startAnimation(setMyDefineAnimation(1));
						list_layout
								.setLayoutAnimation(getAnimationController(1));
						((ImageView) v)
								.setImageResource(R.drawable.open_folders);
						isShowDirListLayout = true;
					}
				}
			}
		});
	}

	private String[] getImageList(String path) {
		String[] ret = null;
		FileTypeFilter pngFileFilter = new FileTypeFilter() {
		};
		pngFileFilter
				.addType(TaylorZeroPicActivitySetting.zero_pic_extern_name);
		File dir = new File(picPath);
		ret = dir.list(pngFileFilter);
		return ret;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != mPlayBgMp3) {
			mPlayBgMp3.doDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (null != mPlayBgMp3 && isOpenVolume) {
			now_volume_pos = mPlayBgMp3.getPlayMp3Position();
			mPlayBgMp3.setStarMp3Position(now_volume_pos);
			mPlayBgMp3.doPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		if (null != mPlayBgMp3 && isOpenVolume) {
			mPlayBgMp3.doResume();
		}
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Fragment getNewDslvFragment(Context context) {
		DSLVFragmentClicks f = DSLVFragmentClicks.newInstance(context,
				mNumHeaders, mNumFooters);
		f.removeMode = mRemoveMode;
		f.removeEnabled = mRemoveEnabled;
		f.dragStartMode = mDragStartMode;
		f.sortEnabled = mSortEnabled;
		f.dragEnabled = mDragEnabled;
		return f;
	}

	@SuppressLint("InlinedApi")
	private Animation setMyDefineAnimation(int kind) {
		int duration = 0;
		Animation animation = null;
		switch (kind) {
		case 1:
			duration = 300;
			animation = new TranslateAnimation(-popDirListWindowX, 0, 0, 0);
			animation.setDuration(duration);
			animation.setInterpolator(mContext,
					android.R.interpolator.accelerate_decelerate);
			break;
		case 2:
			duration = 500;
			animation = new TranslateAnimation(0, -popDirListWindowX, 0, 0);
			animation.setDuration(duration);
			animation.setInterpolator(mContext,
					android.R.interpolator.accelerate_decelerate);
			break;
		}

		return animation;
	}

	/**
	 * Layout动画
	 * 
	 * @return
	 */
	protected LayoutAnimationController getAnimationController(int kind) {
		int duration = 0;
		AnimationSet set = new AnimationSet(true);
		Animation animation = null;
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		switch (kind) {
		case 1:
			duration = 500;
			animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(duration);
			set.addAnimation(animation);
			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
					0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -1.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			duration = 500;
			animation.setDuration(duration);
			set.addAnimation(animation);
			break;
		case 2:
			animation = new AlphaAnimation(1.0f, 0.0f);
			duration = 500;
			animation.setDuration(duration);
			set.addAnimation(animation);
			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
					0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 1.0f);
			duration = 300;
			animation.setDuration(duration);
			set.addAnimation(animation);
			break;
		}

		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				if (null != mOneMirroView) {
					new LoadBitmapThread(picPath + imageDataList[msg.arg1])
							.start();
				}
				break;
			case MSG_HANDLER_UPDATE_DIRLIST_PATH:
				Bundle tmpBundle = msg.getData();
				if (null != tmpBundle) {
					String path = tmpBundle.getString(BUNDLE_KEY_DIR_LIST_PATH);
					picPath = path;
					imageDataList = getImageList(picPath);
					galleryImageAdapter = new ImageAdapter(
							TaylorZeroPicActivity2.this);
					gallery.setAdapter(galleryImageAdapter);
				}
				break;
			case MSG_HANDLER_REFRESH_SHOW_VIEW:
				mOneMirroView.resetNewShowBmp(refreshBmp, refreshBmpPos.left,
						refreshBmpPos.top, 0, 0, 0, 0);
				break;
			}
			super.handleMessage(msg);
		}

	};

	Bitmap refreshBmp = null;
	BmpPosValue refreshBmpPos = null;

	private class LoadBitmapThread extends Thread {
		private String path;

		public LoadBitmapThread(String path) {
			this.path = path;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			refreshBmp = TaylorZeroBmp.loadBitmapAutoSize(path,
					scrInfo.scrWidth, scrInfo.scrHeight);
			if (null != refreshBmp) {
				refreshBmp = TaylorZeroBmp.BitmapRatioMatrix(refreshBmp,
						scrInfo.scrWidth, scrInfo.scrHeight);
				mOneMirroView.viewValue.setDrawBmpCacheBmp(refreshBmp);
				refreshBmpPos = mOneMirroView.viewValue.setDrawBmpCacheBmpPos(
						mContext, refreshBmp,
						TaylorZeroPicturesViewValues.SHOW_BMP_CENTER_HORIZON);
				Message msg = new Message();
				msg.what = MSG_HANDLER_REFRESH_SHOW_VIEW;
				mHandler.sendMessage(msg);
			}
			super.run();
		}
	}

	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = arg2;
			mHandler.sendMessage(msg);
			arg1.setBackgroundResource(R.drawable.gallery_border_select);
			if (null != pre_border_select_view) {
				pre_border_select_view
						.setBackgroundResource(R.drawable.gallery_border_un_select);
			}
			pre_border_select_view = arg1;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View v,
				int position, long id) {
			String temp = "Hello World!";
			Toast toast = Toast.makeText(getBaseContext(),
					"Posituoin is " + id, Toast.LENGTH_SHORT);
			toast.show();
			return true;
		}
	};

	private String[] imageDataList = null;

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater = null;

		public ImageAdapter(Context c) {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			if (imageDataList != null) {
				return imageDataList.length;
			} else {
				return 0;
			}
		}

		public Object getItem(int position) {
			if (position >= imageDataList.length) {
				position = imageDataList.length - 1;
			}

			return position;
		}

		public long getItemId(int position) {
			if (position >= imageDataList.length) {
				position = imageDataList.length - 1;
			}
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = inflater.inflate(
					R.layout.taylorzero_activity_pic2_gallery_imgview, null);
			View view = null;
			if (null != imageDataList) {
				if (null != layout) {
					view = layout.findViewById(R.id.imageItem);
				}

				if (position >= imageDataList.length) {
					position = imageDataList.length - 1;
				}

				if (null != view) {
					Bitmap bmp = loadPreViewBitmap(imageDataList[position]);
					if (null != bmp) {
						((ImageView) view).setImageBitmap(bmp);
					}
				}
			}
			return layout;
		}

		public Bitmap loadPreViewBitmap(String picName) {
			Bitmap ret = null;
			int width, height;
			width = 128;
			height = 96;
			String targetPath = picPath
					+ TaylorZeroPicActivitySetting.zero_pre_view_png_path
					+ File.separator;
			File tmpFile = new File(targetPath + picName);
			if (!tmpFile.exists()) {
				// create new preview pic
				Bitmap bmp = TaylorZeroBmp.loadBitmapAutoSize(
						picPath + picName, width, height);
				if (null != bmp) {
					MyGraphic.SavePicture(targetPath, picName, bmp);
				}
			}
			ret = TaylorZeroBmp.loadBitmapAutoSize(targetPath + picName, width,
					height);

			return ret;
		}

		public int checkPosition(int position) {
			if (position >= imageDataList.length) {
				position = imageDataList.length - 1;
			}

			return position;
		}
	}

	@Override
	public void onEnabledOkClick(boolean drag, boolean sort, boolean remove) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDragOkClick(int removeMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemoveOkClick(int removeMode) {
		// TODO Auto-generated method stub

	}
}
