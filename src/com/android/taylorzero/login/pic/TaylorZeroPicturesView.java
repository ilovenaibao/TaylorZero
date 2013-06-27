package com.android.taylorzero.login.pic;

import java.io.File;
import java.io.FileOutputStream;

import com.android.taylorzero.login.pic.TaylorZeroPicturesViewValues.BmpPosValue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TaylorZeroPicturesView extends View {
	// Debug log
	private String DebugLog = "TaylorZeroPicturesView->";
	private Context parentContext = null;
	// draw bmp on canvas
	public TaylorZeroPicturesViewValues viewValue = null;
	private float touchDown_x, touchDown_y, touchUp_x, touchUp_y;
	private int scrWidth, scrHeight;
	private int isLoadingNextBmp;

	/**
	 * Initialize TaylorZeroPicturesView
	 * 
	 * @param context
	 */
	public TaylorZeroPicturesView(Context context) {
		super(context);
		parentContext = context;
		viewValue = new TaylorZeroPicturesViewValues(context);
		touchDown_x = touchDown_y = touchUp_x = touchDown_y = 0;
		scrWidth = scrHeight = 0;
		isLoadingNextBmp = 0;
	}

	/**
	 * 附带属性的初始化DrawView
	 * 
	 * @param context
	 * @param set
	 */
	public TaylorZeroPicturesView(Context context, AttributeSet set) {
		super(context, set);
		parentContext = context;
		viewValue = new TaylorZeroPicturesViewValues(context);
	}

	public void initializePicturesView(int width, int height) {
		viewValue.createDrawBmpCacheCanvase();
		viewValue.createDrawBmpCache(width, height);
		viewValue.setDrawBmpCacheCanvas();
		viewValue.createDrawPaint();
		scrWidth = width;
		scrHeight = height;
	}

	private void touchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// touchDownEvent(event);
			break;
		case MotionEvent.ACTION_UP:
			// touchUpEvent(event);
			break;
		case MotionEvent.ACTION_MOVE:
			// touchMoveEvent(event);
			break;
		}
	}

	private void touchDownEvent(MotionEvent event) {
		touchDown_x = event.getX();
		touchDown_y = event.getY();
	}

	private void touchUpEvent(MotionEvent event) {
		touchUp_x = event.getX();
		touchUp_y = event.getY();
	}

	private void touchMoveEvent(MotionEvent event) {
		touchUp_x = event.getX();
		touchUp_y = event.getY();
		if (null != viewValue) {
			float dividX = touchUp_x - touchDown_x;
			float dividY = touchUp_y - touchDown_y;
			BmpPosValue tmpShowBmpPos = viewValue.getShowBmpPos();
			tmpShowBmpPos.left += dividX;
			// tmpShowBmpPos.top += dividY;
			if (0 > dividX) {
				int showBmpCount = viewValue.getShowBmpCount();
				if (0 == isLoadingNextBmp
						|| (0 >= tmpShowBmpPos.left && -1 == isLoadingNextBmp)) {
					isLoadingNextBmp = 1;
					viewValue.setNextBmp(showBmpCount + 1, scrWidth, scrHeight,
							isLoadingNextBmp);
				}
			} else if (0 < dividX) {
				int showBmpCount = viewValue.getShowBmpCount();
				if (0 == isLoadingNextBmp
						|| (0 <= tmpShowBmpPos.left && 1 == isLoadingNextBmp)) {
					isLoadingNextBmp = -1;
					viewValue.setNextBmp(showBmpCount - 1, scrWidth, scrHeight,
							isLoadingNextBmp);
				}
			}
			viewValue.setShowBmp(tmpShowBmpPos.left, tmpShowBmpPos.top, 0, 0,
					dividX, dividY);
			touchDown_x = touchUp_x;
			touchDown_y = touchUp_y;
			this.invalidate();
		}
	}

	public void resetNewShowBmp(Bitmap bmp, float left, float top, float right,
			float bottom, float dividX, float dividY) {
		if (null != viewValue) {
			viewValue.setDrawBmpCacheBmp(bmp);
			viewValue.setShowBmp(left, top, right, bottom, dividX, dividY);
			invalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		touchEvent(event);
		return true;
		// return super.onTouchEvent(event);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if (viewValue.realShowBmp != null) {
			if (viewValue.drawPaint != null) {
				canvas.drawBitmap(viewValue.realShowBmp, 0, 0,
						viewValue.drawPaint);
			}
		}
		super.onDraw(canvas);
	}
}
