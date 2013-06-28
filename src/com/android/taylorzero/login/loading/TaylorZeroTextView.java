package com.android.taylorzero.login.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TaylorZeroTextView extends TextView {
	boolean scrollFlag = false;
	boolean additionScrollFlag = false;

	public TaylorZeroTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public TaylorZeroTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public TaylorZeroTextView(Context context) {
		super(context);

	}

	// public void setHorizenScroll(boolean flg) {
	// scrollFlag = flg;
	// }

	@Override
	public boolean isFocused() {
		if (scrollFlag || additionScrollFlag) {
			return true;
		} else {
			return false;
		}
		// return true;
		// return super.isFocused();
	}

}
