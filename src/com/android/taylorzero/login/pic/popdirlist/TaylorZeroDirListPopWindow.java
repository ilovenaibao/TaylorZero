package com.android.taylorzero.login.pic.popdirlist;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.taylorzero.R;

public class TaylorZeroDirListPopWindow {
	private Context parentContext = null;
	public LinearLayout myDirListWindowLayout = null;
	public PopupWindow dirListWindow = null;
	public boolean dirListWindowIsShowing;

	public TaylorZeroDirListPopWindow(Context context, int activity_width,
			int activity_heigth) {
		parentContext = context;
		dirListWindowIsShowing = false;
		InitializeToolsImageButton(context, activity_width, activity_heigth);
		Initialize(context);
	}

	private void Initialize(Context context) {
		if (myDirListWindowLayout != null) {
			View tmpV = (View) ((LinearLayout) myDirListWindowLayout);
			dirListWindow = new PopupWindow(tmpV,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			dirListWindow.setContentView(tmpV);
			dirListWindow.setAnimationStyle(R.style.PopWindowAnimation);
		}
	}

	private void InitializeToolsImageButton(Context context,
			int activity_width, int activity_heigth) {
		myDirListWindowLayout = new LinearLayout(context);
		ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		myDirListWindowLayout.setLayoutParams(param);
		myDirListWindowLayout.setBackgroundDrawable(context.getResources()
				.getDrawable(R.drawable.layout_xml));
		myDirListWindowLayout.setOrientation(LinearLayout.VERTICAL);
		myDirListWindowLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		LinearLayout oneLineLayout = new LinearLayout(context);
		oneLineLayout.setLayoutParams(lp);
		oneLineLayout.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.layout_xml2));
		oneLineLayout.setOrientation(LinearLayout.HORIZONTAL);
		myDirListWindowLayout.addView(oneLineLayout);
	}
}
