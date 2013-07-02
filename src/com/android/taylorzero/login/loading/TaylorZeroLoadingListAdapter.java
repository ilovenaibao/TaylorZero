package com.android.taylorzero.login.loading;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mylib.dbtypeconvert.MyDBTypeConvert;
import com.android.taylorzero.R;

public class TaylorZeroLoadingListAdapter extends BaseAdapter {
	Context mContext;
	// ArrayList<MyOneData> totalList;
	ArrayList<TaylorZeroLoadingOneData> dataList;
	private int TextView_1_Setting_Size = 40;
	Bitmap save_bg_bmp = null;
	Bitmap little_bitmap = null;
	int showSaveBmpSize = 20;
	int padding = 0;

	public TaylorZeroLoadingListAdapter(Context view,
			ArrayList<TaylorZeroLoadingOneData> list) {
		mContext = view;
		dataList = list;
		save_bg_bmp = getResIcon(R.drawable.save_bg);
		little_bitmap = getResIcon(R.drawable.numberbg);
	}

	private Bitmap getResIcon(int resId) {
		Drawable icon;
		icon = mContext.getResources().getDrawable(resId);
		if (icon instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) icon;
			return bd.getBitmap();
		} else {
			return null;
		}
	}

	@Override
	public int getCount() {

		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this.mContext);
		View view = inflater.inflate(R.layout.taylorzero_activity_loading_list,
				null);
		if (null == dataList || 0 >= dataList.size()) {
			return view;
		}
		TaylorZeroLoadingOneData oneData = new TaylorZeroLoadingOneData();
		oneData = dataList.get(position);
		// list tv
		TextView tv_1 = (TextView) view.findViewById(R.id.taylor_loading_tv);
		tv_1.setText(oneData.loadingItemTitle);
		tv_1.setTextSize(TextView_1_Setting_Size);
		Typeface font = Typeface.createFromAsset(mContext.getAssets(),
				"font/cn_test.ttf");
		tv_1.setTypeface(font, Typeface.NORMAL);
		tv_1.isFocusable();
		tv_1.setHorizontallyScrolling(true);
		tv_1.setEllipsize(TruncateAt.MARQUEE);
		tv_1.setMarqueeRepeatLimit(-1);
		// save img
		ImageView downloadImageView = (ImageView) view
				.findViewById(R.id.save_img);
		TextView tmp_tv = (TextView) view.findViewById(R.id.tmp_tv);
		Bitmap number_bitmap = generatorContactCountIcon(save_bg_bmp,
				little_bitmap, 10);
		if (number_bitmap != null) {
			downloadImageView.setImageBitmap(number_bitmap);
			// padding = number_bitmap.getWidth();
			// int tmpPadding = 0;
			// // downloadImageView.setImageBitmap(save_bg_bmp);
			// tmpPadding = (padding - save_bg_bmp.getWidth());
			// tmp_tv.setPadding(0, 0, tmpPadding, 0);
		}

		return view;
	}

	/**
	 * 在給定的圖片上方加上需要下載的flash的個數。數量用紅色表示
	 * 
	 * @param icon
	 *            給定的圖片
	 * @param count
	 *            需要加載的數字
	 * @return 數量的圖片
	 */
	private Bitmap generatorContactCountIcon(Bitmap icon, Bitmap little_icon,
			int count) {
		// 初始化?布
		int width = (int) icon.getWidth() + 14;
		int height = (int) icon.getHeight() + 8;
		// Log.d("download flash!!!", "the icon size is " + width);
		Bitmap contactIcon = Bitmap.createBitmap(width, height,
				Config.ARGB_4444);
		Canvas canvas = new Canvas(contactIcon);

		// 拷??片
		Paint iconPaint = new Paint();
		iconPaint.setDither(true);// 防抖?
		iconPaint.setFilterBitmap(true);// 用??Bitmap?行?波?理，??，?你??Drawable?，?有抗??的效果
		canvas.drawBitmap(icon, 0, 8, iconPaint);

		// ?用抗??和使用??的文本字距
		Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);
		countPaint.setColor(Color.WHITE);

		float textHeight = MyDBTypeConvert.dip2px(mContext, showSaveBmpSize);
		countPaint.setTextSize(textHeight);
		countPaint.setTypeface(Typeface.DEFAULT);
		int strWidth = (int) countPaint.measureText(String.valueOf(count));
		width = width - little_icon.getWidth();
		height = 0;
		canvas.drawBitmap(little_icon, width, height, iconPaint);
		canvas.drawText(String.valueOf(count),
				width + 1 + (little_icon.getWidth() - strWidth) / 2,
				textHeight, countPaint);
		return contactIcon;
	}
}
