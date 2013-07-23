package com.android.taylorzero.content;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.taylorzero.R;

public class TaylorZeroTalkSelectWindowListAdapter extends BaseAdapter {

	Context mContext;
	// ArrayList<MyOneData> totalList;
	ArrayList<TaylorZeroTalkSelectWindowsListOneData> dataList;

	public TaylorZeroTalkSelectWindowListAdapter(Context view) {
		mContext = view;
	}

	public void setDataList(
			ArrayList<TaylorZeroTalkSelectWindowsListOneData> dataList) {
		this.dataList = dataList;
	}

	public TaylorZeroTalkSelectWindowListAdapter(Context view,
			ArrayList<TaylorZeroTalkSelectWindowsListOneData> dataList) {
		mContext = view;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this.mContext);
		View view = inflater.inflate(
				R.layout.taylorzero_talkcontent_select_window_list, null);
		if (null == dataList || 0 >= dataList.size()) {
			return view;
		}

		TaylorZeroTalkSelectWindowsListOneData oneData = new TaylorZeroTalkSelectWindowsListOneData();
		oneData = dataList.get(position);

		TaylorZeroTalkSelectWindowListTextView tv = (TaylorZeroTalkSelectWindowListTextView) view
				.findViewById(R.id.my_list_tv_1);
		tv.setText(oneData.contentStr);
		tv.setTextColor(Color.BLUE);
		tv.setSingleLine(false);
		return view;
	}
}
