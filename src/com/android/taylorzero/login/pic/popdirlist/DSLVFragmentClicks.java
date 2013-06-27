package com.android.taylorzero.login.pic.popdirlist;

import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.taylorzero.R;
import com.android.taylorzero.login.pic.TaylorZeroPicActivity2;
import com.mobeta.android.dslv.MyRefreshListViewCaller;

public class DSLVFragmentClicks extends DSLVFragment {

	com.mobeta.android.dslv.MyRefreshListViewCaller.Caller mCaller = new MyRefreshListViewCaller().new Caller();
	com.mobeta.android.dslv.MyRefreshListViewCaller.Callee mCallee = new MyRefreshListViewCaller().new Callee();

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

	ListView lv = null;
	ImageView preFolderChangeIconView = null;
	int preSelectViewPos = 0;

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		lv = getListView();
		mCaller.SetI(mCallee);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (DSLVFragmentClicks.this.isDataLoadCompelete) {
					String message = String.format("Clicked item %d", arg2);
					if (null != preFolderChangeIconView) {
						preFolderChangeIconView
								.setImageResource(R.drawable.drag);
					} else {
						preSelectViewPos = 0;
						View tmpView = arg0.getChildAt(preSelectViewPos);
						preFolderChangeIconView = (ImageView) tmpView
								.findViewById(R.id.drag_handle);
						preFolderChangeIconView
								.setImageResource(R.drawable.drag);
					}
					ImageView folderIcon = (ImageView) arg1
							.findViewById(R.id.drag_handle);
					folderIcon.setImageResource(R.drawable.drag_select);
					preFolderChangeIconView = folderIcon;
					preSelectViewPos = arg2;
					DSLVFragmentClicks.this.adapter.setSelectPos(arg2);
					Message msg = new Message();
					msg.what = 2;
					Bundle tmpBundle = new Bundle();
					String tmpPath = DSLVFragmentClicks.this.list_content
							.get(arg2) + File.separator;
					tmpBundle.putString(
							TaylorZeroPicActivity2.BUNDLE_KEY_DIR_LIST_PATH,
							tmpPath);
					TaylorZeroPicActivity2 window = (TaylorZeroPicActivity2) TaylorZeroPicActivity2.mContext;
					if (null != window && null != window.mHandler) {
						msg.setData(tmpBundle);
						window.mHandler.sendMessage(msg);
					}
					// DSLVFragmentClicks.this.mDslv.setPreSelectViewInfo(arg0,
					// mCaller, mCallee, preSelectViewPos,
					// preFolderChangeIconView, R.drawable.drag_select,
					// R.drawable.drag, R.id.drag_handle);
					// Toast.makeText(getActivity(), message,
					// Toast.LENGTH_SHORT)
					// .show();
				}
			}
		});
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (DSLVFragmentClicks.this.isDataLoadCompelete) {
					String message = String
							.format("Long-clicked item %d", arg2);
					// Toast.makeText(getActivity(), message,
					// Toast.LENGTH_SHORT)
					// .show();
				}
				return true;
			}
		});
	}
}
