package com.android.taylorzero.login.loading;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.taylorzero.R;

public class TaylorZeroLoadingListAdapter extends BaseAdapter {
	Context mContext;
	// ArrayList<MyOneData> totalList;
	ArrayList<TaylorZeroLoadingOneData> dataList;

	public TaylorZeroLoadingListAdapter(Context view,
			ArrayList<TaylorZeroLoadingOneData> list) {
		mContext = view;
		dataList = list;
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
		View view = inflater.inflate(R.layout.taylorzero_activity_loading_list,
				null);
		if (null == dataList || 0 >= dataList.size()) {
			return view;
		}

		TaylorZeroLoadingOneData oneData = new TaylorZeroLoadingOneData();
		oneData = dataList.get(position);

		return view;
	}
}
