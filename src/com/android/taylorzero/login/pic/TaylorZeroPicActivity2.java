package com.android.taylorzero.login.pic;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.mylib.xscan.FileTypeFilter;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroBmp;
import com.android.taylorzero.login.pic.TaylorZeroPicturesViewValues.BmpPosValue;
import com.android.taylorzero.login.pic.popdirlist.DSLVFragmentClicks;
import com.android.taylorzero.login.pic.popdirlist.DragInitModeDialog;
import com.android.taylorzero.login.pic.popdirlist.EnablesDialog;
import com.android.taylorzero.login.pic.popdirlist.RemoveModeDialog;
import com.android.taylorzero.setting.TaylorZeroPicActivitySetting;
import com.mobeta.android.dslv.DragSortController;

public class TaylorZeroPicActivity2 extends FragmentActivity implements
		RemoveModeDialog.RemoveOkListener, DragInitModeDialog.DragOkListener,
		EnablesDialog.EnabledOkListener {

	private int mNumHeaders = 0;
	private int mNumFooters = 0;

	private int mDragStartMode = DragSortController.ON_DRAG;
	private boolean mRemoveEnabled = true;
	private int mRemoveMode = DragSortController.FLING_REMOVE;
	private boolean mSortEnabled = true;
	private boolean mDragEnabled = true;

	private String mTag = "dslvTag";

	public Context mContext = null;
	private RelativeLayout main_layout = null;
	private String picPath = "";
	TaylorZeroPicturesView mOneMirroView = null;
	MyLibScreenInfo scrInfo = null;
	View pre_border_select_view = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_pic2);
		if (savedInstanceState == null) {
			LinearLayout list_layout = (LinearLayout) findViewById(R.id.list_layout);
			if (null != list_layout) {
				ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) list_layout
						.getLayoutParams();
				lp.width = 300;
				lp.height = 500;
				list_layout.setLayoutParams(lp);
				list_layout.setA
			}
			getSupportFragmentManager().beginTransaction()
					.add(R.id.test_bed, getNewDslvFragment(), mTag).commit();
		}

		mContext = this;
		main_layout = (RelativeLayout) findViewById(R.id.layout1);
		Gallery gallery = (Gallery) findViewById(R.id.mygallery);
		scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
		scrInfo.scrHeight -= 120;
		// scrInfo.scrWidth -= 400;
		picPath = My_Static_Method_Lib.getResAbsolutePath(mContext,
				TaylorZeroPicActivitySetting.save_pic_path, false);
		FileTypeFilter pngFileFilter = new FileTypeFilter() {
		};
		pngFileFilter.addType("zero_pic");
		File dir = new File(picPath);
		String[] pngFileListPath = dir.list(pngFileFilter);
		imageDataList = pngFileListPath;
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

		gallery.setAdapter(new ImageAdapter(TaylorZeroPicActivity2.this));
		gallery.setSpacing(10);
		gallery.setSelection(0);
		gallery.setOnItemLongClickListener(longClickListener);
		gallery.setOnItemSelectedListener(selectListener);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

	private Fragment getNewDslvFragment() {
		DSLVFragmentClicks f = DSLVFragmentClicks.newInstance(mNumHeaders,
				mNumFooters);
		f.removeMode = mRemoveMode;
		f.removeEnabled = mRemoveEnabled;
		f.dragStartMode = mDragStartMode;
		f.sortEnabled = mSortEnabled;
		f.dragEnabled = mDragEnabled;
		return f;
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				if (null != mOneMirroView) {
					Bitmap bmp = TaylorZeroBmp.loadBitmapAutoSize(picPath
							+ imageDataList[msg.arg1], scrInfo.scrWidth,
							scrInfo.scrHeight);
					if (null != bmp) {
						bmp = TaylorZeroBmp.BitmapRatioMatrix(bmp,
								scrInfo.scrWidth, scrInfo.scrHeight);
						mOneMirroView.viewValue.setDrawBmpCacheBmp(bmp);
						BmpPosValue bmpPos = mOneMirroView.viewValue
								.setDrawBmpCacheBmpPos(
										mContext,
										bmp,
										TaylorZeroPicturesViewValues.SHOW_BMP_CENTER_HORIZON);
						mOneMirroView.resetNewShowBmp(bmp, bmpPos.left,
								bmpPos.top, 0, 0, 0, 0);
					}
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = arg2;
			mHandler.sendMessage(msg);
			arg1.setBackgroundResource(R.drawable.border_select);
			if (null != pre_border_select_view) {
				pre_border_select_view
						.setBackgroundResource(R.drawable.border_un_select);
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
					Bitmap bmp = TaylorZeroBmp.loadBitmapAutoSize(picPath
							+ imageDataList[position], 648, 480);
					((ImageView) view).setImageBitmap(bmp);
				}
			}
			return layout;
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
