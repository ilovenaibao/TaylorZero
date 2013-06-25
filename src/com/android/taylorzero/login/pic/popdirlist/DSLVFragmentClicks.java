package com.android.taylorzero.login.pic.popdirlist;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class DSLVFragmentClicks extends DSLVFragment {

	public DSLVFragmentClicks(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static DSLVFragmentClicks newInstance(Context context, int headers,
			int footers) {
		DSLVFragmentClicks f = new DSLVFragmentClicks(context);

		Bundle args = new Bundle();
		args.putInt("headers", headers);
		args.putInt("footers", footers);
		f.setArguments(args);

		return f;
	}

	AdapterView.OnItemLongClickListener mLongClickListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			String message = String.format("Long-clicked item %d", arg2);
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
			return true;
		}
	};

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		ListView lv = getListView();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String message = String.format("Clicked item %d", arg2);
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
						.show();

			}
		});
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String message = String.format("Long-clicked item %d", arg2);
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});
	}
}
