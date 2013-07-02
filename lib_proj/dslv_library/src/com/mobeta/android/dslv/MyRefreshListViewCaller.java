package com.mobeta.android.dslv;

import android.widget.ImageView;

public class MyRefreshListViewCaller {

	public interface MyCallerInterface {
		public int refreshListView(int pos);

		public void setRefreshImageView(ImageView selectView, int selectRes,
				ImageView unSelectView, int unSelectRes);

		public ImageView getRefreshImageView();

		public int getSelectViewNewPos();
	}

	public class Caller {
		private MyCallerInterface mc;

		public Caller() {
		}

		public void SetI(MyCallerInterface mc) {
			this.mc = mc;
		}

		public void refreshListView(int selectRefreshViewPos) {
			if (null != mc) {
				mc.refreshListView(selectRefreshViewPos);
			}
		}
	}

	public class Callee implements MyCallerInterface {
		private ImageView selectRefreshView;
		private int selectRes;
		private ImageView unSelectView;
		private int unSelectRes;
		private int selectRefreshViewPos;

		@Override
		public int refreshListView(int pos) {
			// TODO Auto-generated method stub
			if (null != selectRefreshView) {
				selectRefreshView.setImageResource(selectRes);
			}
			if (null != unSelectView) {
				unSelectView.setImageResource(unSelectRes);
			}
			selectRefreshViewPos = pos;
			return 0;
		}

		@Override
		public ImageView getRefreshImageView() {
			// TODO Auto-generated method stub
			return selectRefreshView;
		}

		@Override
		public void setRefreshImageView(ImageView selectView, int selectRes,
				ImageView unSelectView, int unSelectRes) {
			// TODO Auto-generated method stub
			selectRefreshView = selectView;
			this.selectRes = selectRes;
			this.unSelectView = unSelectView;
			this.unSelectRes = unSelectRes;
		}

		@Override
		public int getSelectViewNewPos() {
			// TODO Auto-generated method stub
			return selectRefreshViewPos;
		}
	}
}
