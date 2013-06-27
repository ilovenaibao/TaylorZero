package com.android.taylorzero.login.pic.popdirlist;

import java.util.List;

import com.android.taylorzero.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	private int selectPos = 0;

	public MyArrayAdapter(Context context, int resource,
			int textViewResourceId, List<String> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public void setSelectPos(int pos) {
		selectPos = pos;
	}

	public int getSelectPos() {
		return selectPos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = super.getView(position, convertView, parent);
		ImageView tmpImageView = (ImageView) v.findViewById(R.id.drag_handle);
		if (position == selectPos) {
			tmpImageView.setImageResource(R.drawable.drag_select);
		} else {
			tmpImageView.setImageResource(R.drawable.drag);
		}
		return v;
	}

}
