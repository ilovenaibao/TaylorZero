package com.android.taylorzero.login.pic.popdirlist;

import java.io.File;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.android.taylorzero.TaylorZeroPlayBgMp3CallBack.Caller;
import com.android.taylorzero.setting.TaylorZeroPicActivitySetting;

public class MySearchDirListService extends Service {
	// Debug tag
	private String DebugTag = "DownLoadService";
	public static final int MSG_REGISTER_CLIENT = 0x01;
	public static final int MSG_UPDATE_LIST_DATA = 0x02;
	public static final int MSG_UPDATE_LIST_DATA_RESULT = 0x03;

	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	public String pkg_name;
	public Caller caller = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mMessenger.getBinder();
	}

	class IncomingHandlerFromTreeList extends Handler {
		Messenger tmpMessenger = null;

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UPDATE_LIST_DATA:
				new UpdateDataThread(msg.getData()).start();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	ArrayList<String> getZeroPicDirs = new ArrayList<String>();

	private void getZeroPicDirsOnePath(File[] dirFiles, String parenPath) {
		boolean isCheckThisFolder = false;
		for (int i = 0; null != dirFiles && i < dirFiles.length; i++) {
			File oneDir = dirFiles[i];
			if (oneDir.isDirectory()) {
				File[] nextDirFiles = oneDir.listFiles();
				getZeroPicDirsOnePath(nextDirFiles, oneDir.getAbsolutePath());
			} else {
				String absolutePath = oneDir.getAbsolutePath();
				if (!isCheckThisFolder
						&& 0 <= absolutePath.indexOf(".png")
						|| 0 <= absolutePath
								.indexOf("."
										+ TaylorZeroPicActivitySetting.zero_pic_extern_name)) {
					isCheckThisFolder = true;
					getZeroPicDirs.add(parenPath);
				}
			}
		}
	}

	final Messenger mMessenger = new Messenger(
			new IncomingHandlerFromTreeList());

	private class UpdateDataThread extends Thread {
		Bundle bundle = null;

		public UpdateDataThread(Bundle bundle) {
			this.bundle = bundle;
		}

		public void run() {
			// Loading all list view data
			ArrayList<String> zeroPicSearchPath = null;
			if (null != bundle) {
				zeroPicSearchPath = bundle
						.getStringArrayList(DSLVFragment.SEARCH_PATH_KEY);
				for (int i = 0; null != zeroPicSearchPath
						&& i < zeroPicSearchPath.size(); i++) {
					String onePath = zeroPicSearchPath.get(i);
					File oneFile = new File(onePath);
					File[] dirList = oneFile.listFiles();
					getZeroPicDirsOnePath(dirList, onePath);
				}
			}

			// replay to tree list refresh list data
			Message msgTo = Message.obtain(null, MSG_UPDATE_LIST_DATA_RESULT);
			if (null != mClients && 0 < mClients.size()) {
				try {
					mClients.get(0).send(msgTo);
				} catch (RemoteException e) {
					mClients.remove(0);
				}
			}
		}
	}
}
