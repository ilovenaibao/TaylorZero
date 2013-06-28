package com.android.taylorzero.login.pic.popdirlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroPicActivitySetting;
import com.android.taylorzero.setting.TaylorZeroSetting;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

public class DSLVFragment extends ListFragment {
	Context parentContext = null;
	public boolean isDataLoadCompelete = false;

	public MyArrayAdapter adapter;

	private String[] array;
	public ArrayList<String> list_show_title = new ArrayList<String>();
	public ArrayList<String> list_content = new ArrayList<String>();

	public DSLVFragment(Context context) {
		parentContext = context;
	}

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapter.getItem(from);
				adapter.remove(item);
				adapter.insert(item, to);
			}
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			adapter.remove(adapter.getItem(which));
		}
	};

	protected int getLayout() {
		// this DSLV xml declaration does not call for the use
		// of the default DragSortController; therefore,
		// DSLVFragment has a buildController() method.
		return R.layout.dslv_fragment_main;
	}

	/**
	 * Return list item layout resource passed to the ArrayAdapter.
	 */
	protected int getItemLayout() {
		/*
		 * if (removeMode == DragSortController.FLING_LEFT_REMOVE || removeMode
		 * == DragSortController.SLIDE_LEFT_REMOVE) { return
		 * R.layout.list_item_handle_right; } else
		 */
		if (removeMode == DragSortController.CLICK_REMOVE) {
			return R.layout.dslv_list_item_click_remove;
		} else {
			return R.layout.dslv_list_item_handle_left;
		}
	}

	public DragSortListView mDslv;
	public DragSortController mController;

	public int dragStartMode = DragSortController.ON_DOWN;
	public boolean removeEnabled = false;
	public int removeMode = DragSortController.FLING_REMOVE;
	public boolean sortEnabled = true;
	public boolean dragEnabled = true;

	public static DSLVFragment newInstance(Context context, int headers,
			int footers) {
		DSLVFragment f = new DSLVFragment(context);

		Bundle args = new Bundle();
		args.putInt("headers", headers);
		args.putInt("footers", footers);
		f.setArguments(args);

		return f;
	}

	public DragSortController getController() {
		return mController;
	}

	// add by Taylor
	private String DebugTag = "MySearchDirListService->";
	private boolean debugFlag = false;
	// local service bound flag
	Messenger mService = null;
	boolean mIsBound = false;
	public final static String SEARCH_PATH_KEY = "ZERO_SEARCH_PATH_KEY";
	public final static String SEARCH_PATH_TITLE_KEY = "ZERO_SEARCH_PATH_TITLE_KEY";

	/**
	 * 
	 * 绑定并启动服务，bind按钮点击时会调用这个方法。
	 */
	public void doBindSearchDirListService() {
		// 绑定并启动服务。
		mIsBound = parentContext.bindService(new Intent(parentContext,
				MySearchDirListService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	class IncomingHandlerFromLocalService extends Handler {
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			switch (msg.what) {
			case MySearchDirListService.MSG_UPDATE_LIST_DATA_RESULT:
				if (null != bundle) {
					ArrayList<String> zeroPicPath = bundle
							.getStringArrayList(SEARCH_PATH_KEY);
					ArrayList<String> zeroPicPathTitle = bundle
							.getStringArrayList(SEARCH_PATH_TITLE_KEY);
					if (null != zeroPicPath && null != zeroPicPathTitle) {
						list_content = zeroPicPath;
						list_show_title = zeroPicPathTitle;
						if (null != adapter) {
							updateLisAdapter();
							isDataLoadCompelete = true;
						}
					}
				}
				doUnbindLocalService();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	final Messenger mMessenger = new Messenger(
			new IncomingHandlerFromLocalService());

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 从IBinder对象中获取服务实例。
			if (debugFlag) {
				Log.i(DebugTag, "bind local service start");
			}
			mService = new Messenger(service);
			try {
				// registe service
				Message msg = Message.obtain(null,
						MySearchDirListService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
				// update listview data
				msg = Message.obtain(null,
						MySearchDirListService.MSG_UPDATE_LIST_DATA);
				msg.replyTo = mMessenger;
				mService.send(msg);
				Bundle mBundle = new Bundle();
				ArrayList<String> zeroSearchPathArray = new ArrayList<String>();
				// String path = My_Static_Method_Lib.getResAbsolutePath(
				// parentContext,
				// TaylorZeroPicActivitySetting.save_pic_path, false);
				String path = TaylorZeroSetting.Zero_Data_Real_Path
						+ TaylorZeroPicActivitySetting.save_pic_path;
				File tmpFile = new File(path);
				if (!tmpFile.exists()) {
					Toast.makeText(parentContext,
							R.string.application_data_err, Toast.LENGTH_SHORT)
							.show();
					((Activity) parentContext).finish();
					return;
				}
				if (null != path) {
					zeroSearchPathArray.add(path);
				}
				mBundle.putStringArrayList(SEARCH_PATH_KEY, zeroSearchPathArray);
				msg.setData(mBundle);
			} catch (Exception e) {

			}
		}
	};

	/**
	 * 
	 * 解除与服务的绑定，unbind按钮被点击时会调用这个方法
	 */
	void doUnbindLocalService() {
		// 如果服务被绑定，则解除与服务绑定。
		if (mIsBound) {
			try {
				parentContext.unbindService(mConnection);
			} catch (Exception e) {

			}
			mIsBound = false;
		}
	}

	// add end

	/**
	 * Called from DSLVFragment.onActivityCreated(). Override to set a different
	 * adapter.
	 */
	public void setListAdapter() {
		// array = getResources().getStringArray(R.array.jazz_artist_names);
		array = new String[1];
		array[0] = "default";
		list_show_title = new ArrayList<String>(Arrays.asList(array));
		adapter = new MyArrayAdapter(getActivity(), getItemLayout(), R.id.text,
				list_show_title);
		setListAdapter(adapter);
	}

	public void updateLisAdapter() {
		adapter = new MyArrayAdapter(getActivity(), getItemLayout(), R.id.text,
				list_show_title);
		adapter.setSelectPos(0);
		setListAdapter(adapter);
	}

	/**
	 * Called in onCreateView. Override this to provide a custom
	 * DragSortController.
	 */
	public DragSortController buildController(DragSortListView dslv) {
		// defaults are
		// dragStartMode = onDown
		// removeMode = flingRight
		DragSortController controller = new DragSortController(dslv);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setClickRemoveId(R.id.click_remove);
		controller.setRemoveEnabled(removeEnabled);
		controller.setSortEnabled(sortEnabled);
		controller.setDragInitMode(dragStartMode);
		controller.setRemoveMode(removeMode);
		controller.setParentContext(parentContext);
		return controller;
	}

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDslv = (DragSortListView) inflater.inflate(getLayout(), container,
				false);

		mController = buildController(mDslv);
		mDslv.setFloatViewManager(mController);
		mDslv.setOnTouchListener(mController);
		mDslv.setDragEnabled(dragEnabled);

		return mDslv;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isDataLoadCompelete = false;
		doBindSearchDirListService();
		mDslv = (DragSortListView) getListView();

		mDslv.setDropListener(onDrop);
		mDslv.setRemoveListener(onRemove);

		Bundle args = getArguments();
		int headers = 0;
		int footers = 0;
		if (args != null) {
			headers = args.getInt("headers", 0);
			footers = args.getInt("footers", 0);
		}

		for (int i = 0; i < headers; i++) {
			addHeader(getActivity(), mDslv);
		}
		for (int i = 0; i < footers; i++) {
			addFooter(getActivity(), mDslv);
		}

		setListAdapter();
	}

	public static void addHeader(Activity activity, DragSortListView dslv) {
		LayoutInflater inflater = activity.getLayoutInflater();
		int count = dslv.getHeaderViewsCount();

		TextView header = (TextView) inflater.inflate(
				R.layout.dslv_header_footer, null);
		header.setText("Header #" + (count + 1));

		dslv.addHeaderView(header, null, false);
	}

	public static void addFooter(Activity activity, DragSortListView dslv) {
		LayoutInflater inflater = activity.getLayoutInflater();
		int count = dslv.getFooterViewsCount();

		TextView footer = (TextView) inflater.inflate(
				R.layout.dslv_header_footer, null);
		footer.setText("Footer #" + (count + 1));

		dslv.addFooterView(footer, null, false);
	}

}
