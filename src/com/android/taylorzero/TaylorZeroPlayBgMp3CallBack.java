package com.android.taylorzero;

import android.os.Debug;
import android.util.Log;

public class TaylorZeroPlayBgMp3CallBack {
	private String DebugTag = "TaylorZeroPlayBgMp3CallBack ->";

	public interface MyCallInterface {

		public void reFreshView();
	}

	public class Caller {
		private MyCallInterface mc;

		public Caller() {
		}

		public void SetId(MyCallInterface mc) {
			this.mc = mc;
		}

		// Caller的调用方法
		public void refreshView() {
			if (null != mc) {
				// mc.reFreshView(mc.getMyListAdapter());
			}
		}
	}

	public class Callee implements MyCallInterface {

		public Callee() {

		}

		@Override
		public void reFreshView() {
			Log.i(DebugTag, "reFresh!!!");
			// if (null != myAdapter) {
			// Log.i(DebugTag, "reFresh");
			// myAdapter.notifyDataSetChanged();
			// }
		}

		public void setMyListAdapter() {
			// this.myAdapter = myAdapter;
		}

		// @Override
		// public MyListAdapter getMyListAdapter() {
		// return myAdapter;
		// }
	}
}
