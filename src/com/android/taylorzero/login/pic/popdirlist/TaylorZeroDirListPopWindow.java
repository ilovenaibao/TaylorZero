package com.android.taylorzero.login.pic.popdirlist;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.android.taylorzero.R;
import com.mobeta.android.dslv.DragSortController;

public class TaylorZeroDirListPopWindow {
	private Context parentContext = null;
	public boolean dirListWindowIsShowing;
	public PopupWindow myListWindow;

	private String mTag = "dslvTag";
	private int mNumHeaders = 0;
	private int mNumFooters = 0;
	private int mDragStartMode = DragSortController.ON_DRAG;
	private boolean mRemoveEnabled = true;
	private int mRemoveMode = DragSortController.FLING_REMOVE;
	private boolean mSortEnabled = true;
	private boolean mDragEnabled = true;

	public TaylorZeroDirListPopWindow(Context context) {
		parentContext = context;
		dirListWindowIsShowing = false;
		// Taylor add
		View dirListView = ((Activity) context).getLayoutInflater().inflate(
				R.layout.dslv_dir_list_window, null);
		RelativeLayout layout = (RelativeLayout) (dirListView
				.findViewById(R.id.layout_save_loading_main));
		LinearLayout list_layout = (LinearLayout) dirListView
				.findViewById(R.id.list_layout_1);
		if (null != list_layout) {
			ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) list_layout
					.getLayoutParams();
			lp.width = 300;
			lp.height = 500;
			list_layout.setLayoutParams(lp);
		}
		initializeFragment(context, dirListView);
		myListWindow = new PopupWindow(dirListView,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		myListWindow.setContentView(dirListView);
		// myListWindow.setFocusable(true);
		myListWindow.setAnimationStyle(R.style.PopWindowAnimation);
		// end
	}

	private void initializeFragment(Context context, View v) {
		FragmentActivity tmpFragmentActivity = new FragmentActivity();
		tmpFragmentActivity.getSupportFragmentManager().beginTransaction()
				.add(R.id.test_bed_1, getNewDslvFragment(), mTag).commit();
	}

	private Fragment getNewDslvFragment() {
		DSLVFragmentClicks f = DSLVFragmentClicks.newInstance(parentContext,
				mNumHeaders, mNumFooters);
		f.removeMode = mRemoveMode;
		f.removeEnabled = mRemoveEnabled;
		f.dragStartMode = mDragStartMode;
		f.sortEnabled = mSortEnabled;
		f.dragEnabled = mDragEnabled;
		return f;
	}
}
