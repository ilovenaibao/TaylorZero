package com.android.taylorzero.login.pic;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TaylorZeroPicturesViewValues {
	public final static int SHOW_BMP_CENTER_HORIZON = 0x0001;
	public final static int SHOW_BMP_CENTER_VERTICAL = 0x0002;
	public final static int SHOW_BMP_CENTER = 0x0003;
	public final static int SHOW_BMP_LEFT = 0x0004;
	public final static int SHOW_BMP_RIGHT = 0x0005;
	public final static int SHOW_BMP_TOP = 0x0006;
	public final static int SHOW_BMP_BOTTOM = 0x0007;

	public String bmpPath;
	public Bitmap preBmp;
	public Bitmap curBmp;
	public Bitmap nextBmp;
	public Bitmap realShowBmp;
	public Canvas drawBmpCacheCanvas;
	public Paint drawPaint;

	public static class BmpPosValue {
		public float left;
		public float right;
		public float top;
		public float bottom;

		public BmpPosValue() {
			left = right = top = bottom = 0;
		}
	}

	public TaylorZeroPicturesViewValues() {
		bmpPath = null;
		curBmp = realShowBmp = null;
		drawBmpCacheCanvas = null;
		drawPaint = null;
	}

	public void setLoadingBmpPath(String path) {
		bmpPath = path;
	}

	public void setDrawBmpCacheBmp(Bitmap bmp) {
		curBmp = bmp;
	}

	public BmpPosValue setDrawBmpCacheBmpPos(Context context, int style) {
		BmpPosValue ret = null;
		if (null != curBmp) {
			ret = new BmpPosValue();
			int bmpWidth, bmpHeight;
			bmpWidth = curBmp.getWidth();
			bmpHeight = curBmp.getHeight();
			MyLibScreenInfo scrInfo = MyLibScreenSetting.GetScreenSize(context,
					1);
			float value_x, value_y;
			value_x = value_y = 0;
			switch (style) {
			case SHOW_BMP_CENTER_HORIZON:
				break;
			case SHOW_BMP_CENTER_VERTICAL:
				break;
			case SHOW_BMP_CENTER:
				value_x = (scrInfo.scrWidth - bmpWidth) / 2;
				value_y = (scrInfo.scrHeight - bmpHeight) / 2;
				break;
			case SHOW_BMP_LEFT:
				break;
			case SHOW_BMP_RIGHT:
				break;
			case SHOW_BMP_TOP:
				break;
			case SHOW_BMP_BOTTOM:
				break;
			}

			ret.left = value_x;
			ret.top = value_y;
		}
		return ret;
	}

	public void setShowBmp(float left, float top, float right, float bottom) {
		if (null != curBmp && null != drawBmpCacheCanvas && null != drawPaint) {
			drawBmpCacheCanvas.drawBitmap(curBmp, left, top, drawPaint);
			// drawBmpCacheCanvas.drawRect(left, top, right, bottom, drawPaint);
		}
	}

	/**
	 * create drawBmpCache bitmap
	 * 
	 * @param width
	 *            The width of drawBmpCache
	 * @param height
	 *            The height of drawBmpCache
	 * @return Is create success?
	 */
	public boolean createDrawBmpCache(int width, int height) {
		boolean bRet = false;
		// curBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		realShowBmp = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		if (null != curBmp) {
			curBmp.eraseColor(Color.RED);
		}
		if (null != realShowBmp) {
			realShowBmp.eraseColor(Color.TRANSPARENT);
		}
		if (curBmp != null) {
			bRet = true;
		}
		return bRet;
	}

	public void createDrawBmpCacheCanvase() {
		if (null == drawBmpCacheCanvas) {
			drawBmpCacheCanvas = new Canvas();
		}
	}

	/**
	 * set drawBmp on Canvas
	 * 
	 * @return Is success?
	 */
	public boolean setDrawBmpCacheCanvas() {
		boolean bRet = false;
		if (null != realShowBmp) {
			drawBmpCacheCanvas.setBitmap(realShowBmp);
			bRet = true;
		}

		return bRet;
	}

	/**
	 * create draw paint
	 * 
	 * @return Is create success?
	 */
	public boolean createDrawPaint() {
		boolean bRet = false;
		drawPaint = new Paint();
		drawPaint.setColor(Color.RED);
		// 設置畫筆的風格
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeWidth(4);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		drawPaint.setFilterBitmap(true);
		// 反鋸齒
		drawPaint.setAntiAlias(true);
		drawPaint.setDither(true);
		if (drawPaint != null) {
			bRet = true;
		}
		return bRet;
	}

}
