package com.android.taylorzero.content;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.taylorzero.R;

public class TaylorZeroTalkContentSelectWindow {

	private Context mContext = null;
	public PopupWindow TalkContentWindow = null;
	MyLibScreenInfo scrInfo = null;
	ListView selectListView = null;
	ArrayList<TaylorZeroTalkSelectWindowsListOneData> listData = null;
	TaylorZeroTalkSelectWindowListAdapter adapter = null;

	public TaylorZeroTalkContentSelectWindow(Context context) {
		mContext = context;
		View selectView = ((Activity) context).getLayoutInflater().inflate(
				R.layout.taylorzero_talkcontent_selectwindow, null);
		LinearLayout layout = (LinearLayout) (selectView
				.findViewById(R.id.layout_talk_select_window));
		if (null != layout) {
			scrInfo = MyLibScreenSetting.GetScreenSize(mContext, 1);
			layout.setBackgroundColor(0xA0000000);
			layout.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.save_data_loading_dlg));
			TalkContentWindow = new PopupWindow(selectView,
					scrInfo.scrWidth / 2, scrInfo.scrHeight / 2);
			TalkContentWindow.setContentView(selectView);
			TalkContentWindow.setFocusable(true);
			selectListView = (ListView) selectView
					.findViewById(R.id.talk_select_window_list);
		}
	}

	public void setPopWindowListContent(
			ArrayList<TaylorZeroTalkSelectWindowsListOneData> data) {
		listData = data;
	}

	public void setListView() {
		if (null != selectListView) {
			adapter = new TaylorZeroTalkSelectWindowListAdapter(mContext,
					listData);
			selectListView.setAdapter(adapter);
			selectListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if (null != TalkContentWindow
							&& TalkContentWindow.isShowing()) {
						TalkContentWindow.dismiss();
					}
				}
			});
		}
	}
}
