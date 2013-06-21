package com.android.taylorzero.login.pic;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.TaylorZeroBmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class TaylorZeroPicturesViewValues {
	public final static int SHOW_BMP_CENTER_HORIZON = 0x0001;
	public final static int SHOW_BMP_CENTER_VERTICAL = 0x0002;
	public final static int SHOW_BMP_CENTER = 0x0003;
	public final static int SHOW_BMP_LEFT = 0x0004;
	public final static int SHOW_BMP_RIGHT = 0x0005;
	public final static int SHOW_BMP_TOP = 0x0006;
	public final static int SHOW_BMP_BOTTOM = 0x0007;

	private Context mContext;
	// loading bitmap path
	public String bmpPath;
	// current laoding bitmap
	public Bitmap curBmp;
	// laoding next bitmap
	public Bitmap nextBmp;
	// loading pre bitmap
	public Bitmap preBmp;
	// background bitmap
	public Bitmap bgBmp;
	// show combining bitmap
	public Bitmap realShowBmp;
	// show combining bitmap canvas
	public Canvas drawBmpCacheCanvas;
	// draw canvas paint
	public Paint drawPaint;
	// now bmp show pos
	BmpPosValue showBmpPos;
	// loading bitmap list path
	String pngParenPath;
	String[] loadBmpList;
	// loading now bmp count
	int showBmpCount;
	// scr width & height
	private int scrWidth, scrHeight;

	public static class BmpPosValue {
		public float left;
		public float right;
		public float top;
		public float bottom;

		public BmpPosValue() {
			left = right = top = bottom = 0;
		}
	}

	public TaylorZeroPicturesViewValues(Context context) {
		mContext = context;
		bmpPath = null;
		curBmp = nextBmp = preBmp = bgBmp = realShowBmp = null;
		drawBmpCacheCanvas = null;
		drawPaint = null;
		showBmpPos = new BmpPosValue();
		pngParenPath = null;
		loadBmpList = null;
		showBmpCount = 0;
		scrWidth = scrHeight = 0;
	}

	/**
	 * @describe set loading bitmap list
	 * 
	 * @param path
	 *            : png file parent path
	 * @param pathList
	 *            : all of png file name
	 */
	public void setLoadingBitmapList(String path, String[] pathList) {
		pngParenPath = path;
		loadBmpList = pathList;
	}

	/**
	 * @describe set next loading bitmap
	 * 
	 * @param pathCount
	 *            : next loading bitmap count
	 * @param width
	 *            : resize loading bitmap's width
	 * @param height
	 *            : resize loading bitmap's height
	 * @return int : return now next bitmap count
	 */
	public int setNextBmp(int pathCount, int width, int height,
			int chosPreOrNext) {
		if (null != loadBmpList && 0 <= pathCount
				&& loadBmpList.length > pathCount) {
			Bitmap tmpBmp = TaylorZeroBmp.loadBitmapAutoSize(pngParenPath
					+ loadBmpList[pathCount], width, height);
			if (null != tmpBmp) {
				tmpBmp = TaylorZeroBmp.BitmapRatioMatrix(tmpBmp, width, height);
				if (1 == chosPreOrNext) {
					nextBmp = tmpBmp;
				} else if (-1 == chosPreOrNext) {
					preBmp = tmpBmp;
				}
				showBmpCount = pathCount;
			}
		}
		return showBmpCount;
	}

	/**
	 * @describe get show bitmap count
	 * 
	 * @return int : return show bitmap count
	 */
	public int getShowBmpCount() {
		return showBmpCount;
	}

	public void setBackGroundBitmap(int resource, int width, int height) {
		if (null != mContext) {
			bgBmp = TaylorZeroBmp.loadBitmapAutoSize(mContext, resource, width,
					height);
		}
	}

	/**
	 * @describe set loading graphic absolute path
	 * 
	 * @param path
	 *            : bmp path
	 */
	public void setLoadingBmpPath(String path) {
		bmpPath = path;
	}

	/**
	 * @Describe set draw canvas bmp
	 * 
	 * @param bmp
	 *            : will loading bmp
	 */
	public void setDrawBmpCacheBmp(Bitmap bmp) {
		curBmp = bmp;
	}

	/**
	 * @describe set show bmp pos
	 * 
	 * @param context
	 *            : Context
	 * @param style
	 *            : show bmp pos : center, left, right, top, bottom
	 * @return BmpPosValue : return BmpPosValue
	 */
	public BmpPosValue setDrawBmpCacheBmpPos(Context context, Bitmap bmp,
			int style) {
		BmpPosValue ret = null;
		if (null != bmp) {
			ret = new BmpPosValue();
			int bmpWidth, bmpHeight;
			bmpWidth = bmp.getWidth();
			bmpHeight = bmp.getHeight();
			MyLibScreenInfo scrInfo = MyLibScreenSetting.GetScreenSize(context,
					1);
			float value_x, value_y;
			value_x = value_y = 0;
			switch (style) {
			case SHOW_BMP_CENTER_HORIZON:
				value_x = (scrInfo.scrWidth - bmpWidth) / 2;
				break;
			case SHOW_BMP_CENTER_VERTICAL:
				value_y = (scrInfo.scrHeight - bmpHeight) / 2;
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

	/**
	 * @describe set show bmp pos
	 * 
	 * @param bmpPos
	 *            : BmpPosValue
	 */
	public void setShowBmp(BmpPosValue bmpPos) {
		if (null != curBmp && null != drawBmpCacheCanvas && null != drawPaint) {
			if (null != bgBmp) {
				drawBmpCacheCanvas.drawBitmap(bgBmp, 0, 0, drawPaint);
			}
			drawBmpCacheCanvas.drawBitmap(curBmp, bmpPos.left, bmpPos.top,
					drawPaint);
			// set show bmp pos
			showBmpPos = bmpPos;
			// drawBmpCacheCanvas.drawRect(left, top, right, bottom, drawPaint);
		}
	}

	/**
	 * @describe set show bmp pos
	 * 
	 * @param left
	 *            : screen's left
	 * @param top
	 *            : screen's top
	 * @param right
	 *            : screen's right
	 * @param bottom
	 *            : screen's bottom
	 */
	public void setShowBmp(float left, float top, float right, float bottom,
			float dividX, float dividY) {
		if (null != curBmp && null != drawBmpCacheCanvas && null != drawPaint) {
			if (null != bgBmp) {
				drawBmpCacheCanvas.drawBitmap(bgBmp, 0, 0, drawPaint);
			}
			drawBmpCacheCanvas.drawBitmap(curBmp, left, top, drawPaint);
			if (null != nextBmp) {
				BmpPosValue tmpNextBmpPos = setDrawBmpCacheBmpPos(mContext,
						nextBmp, TaylorZeroPicturesViewValues.SHOW_BMP_CENTER);
				drawBmpCacheCanvas.drawBitmap(nextBmp, scrWidth + left,
						tmpNextBmpPos.top, drawPaint);
			}
			// set show bmp pos
			showBmpPos.left = left;
			showBmpPos.top = top;
			showBmpPos.right = right;
			showBmpPos.bottom = bottom;
			// drawBmpCacheCanvas.drawRect(left, top, right, bottom, drawPaint);
		}
	}

	/**
	 * @describe get now show bmp pos
	 * 
	 * @return BmpPosValue : return now show bmp pos
	 */
	public BmpPosValue getShowBmpPos() {
		return showBmpPos;
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
		scrWidth = width;
		scrHeight = height;
		return bRet;
	}

	/**
	 * @describe create draw bmp canvas
	 * 
	 */
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
