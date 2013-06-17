package com.android.taylorzero.login.pic;

import java.io.File;
import java.io.FileOutputStream;

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

	/**
	 * Initialize TaylorZeroPicturesView
	 * 
	 * @param context
	 */
	public TaylorZeroPicturesView(Context context) {
		super(context);
		parentContext = context;
		viewValue = new TaylorZeroPicturesViewValues();
		touchDown_x = touchDown_y = touchUp_x = touchDown_y = 0;
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
		viewValue = new TaylorZeroPicturesViewValues();
	}

	public void initializePicturesView(int width, int height) {
		viewValue.createDrawBmpCacheCanvase();
		viewValue.createDrawBmpCache(width, height);
		viewValue.setDrawBmpCacheCanvas();
		viewValue.createDrawPaint();
	}

	private void touchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
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

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		touchEvent(event);
		return super.onTouchEvent(event);
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
