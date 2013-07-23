package com.android.taylorzero.content;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TaylorZeroTalkSelectWindowListTextView extends TextView {
	boolean scrollFlag = false;
	boolean additionScrollFlag = false;

	public TaylorZeroTalkSelectWindowListTextView(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public TaylorZeroTalkSelectWindowListTextView(Context context,
			AttributeSet attrs) {
		super(context, attrs);

	}

	public TaylorZeroTalkSelectWindowListTextView(Context context) {
		super(context);

	}

	@Override
	public boolean isFocused() {
		if (scrollFlag || additionScrollFlag) {
			return true;
		} else {
			return false;
		}
	}

}
