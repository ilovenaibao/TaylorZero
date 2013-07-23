package com.android.taylorzero.content;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TaylorZeroDefineTextView extends TextView {

	public TaylorZeroDefineTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TaylorZeroDefineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TaylorZeroDefineTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public OnClickListener click_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
