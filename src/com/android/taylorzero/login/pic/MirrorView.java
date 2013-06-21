package com.android.taylorzero.login.pic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class MirrorView extends View {

	Paint m_paint;
	int m_nShadowH;
	Drawable m_dw;
	Bitmap m_bitmap;
	Matrix mMatrix;
	int shadowHeight;

	public MirrorView(Context context) {
		super(context);
		_Init();
	}

	public MirrorView(Context context, Bitmap bitmap) {
		super(context);
		m_bitmap = bitmap;
		_Init();
	}

	public void setBitmap(Bitmap bmp) {
		m_bitmap = bmp;
		_Init();
		this.invalidate();
	}

	private void _Init() {
		// m_dw = new
		// BitmapDrawable(BitmapFactory.decodeResource(getResources(),
		// R.drawable.icon));
		m_dw = new BitmapDrawable(m_bitmap);
		m_dw.setBounds(0, 0, m_dw.getIntrinsicWidth(),
				m_dw.getIntrinsicHeight());
		m_nShadowH = m_dw.getIntrinsicHeight() / 1;
		m_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		LinearGradient lg = new LinearGradient(0, 0, 0, m_nShadowH, 0xB0FFFFFF,
				m_nShadowH, Shader.TileMode.CLAMP);
		m_paint.setShader(lg);
		m_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		mMatrix = new Matrix();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int nX = 0;
		int nY = 0;
		_DrawNormalImg(canvas, nX, nY);
		_DrawMirror(canvas, nX, nY);
	}

	private void _DrawNormalImg(Canvas canvas, int nX, int nY) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.translate(nX, nY);
		m_dw.draw(canvas);
		canvas.restore();
	}

	private void _DrawMirror(Canvas canvas, int nX, int nY) {
		int nW = m_dw.getIntrinsicWidth();
		int nH = m_dw.getIntrinsicHeight();
		shadowHeight = nH / 2;
		float[] src = { 0, nH, nW, nH, nW, nH - m_nShadowH, 0, nH - m_nShadowH };
		float[] dst = { 0, nH, nW, nH, nW, shadowHeight, 0, shadowHeight };
		canvas.save();
		mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
		canvas.concat(mMatrix);
		// draw mirror image
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(1.0f, -1.0f);
		canvas.translate(nX, -(nY + nH * 2));
		canvas.clipRect(0, nH, nW, nH - m_nShadowH);
		m_dw.draw(canvas);
		canvas.restore();
		// draw mask
		canvas.save();
		canvas.translate(nX, nY + nH);
		canvas.drawRect(0, 0, nW, m_nShadowH, m_paint);
		canvas.restore();
		canvas.restore();
	}
}
