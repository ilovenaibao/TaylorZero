package com.android.taylorzero.login.loading;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.setting.TaylorZeroSetting;
import com.android.taylorzero.setting.TaylorZeroStartActivitySetting;

public class TaylorZeroLoadingActivity extends Activity {

	private Context mContext = null;
	private ArrayList<TaylorZeroLoadingOneData> dataList = new ArrayList<TaylorZeroLoadingOneData>();
	private ListView lstView = null;
	private TaylorZeroLoadingListAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_loading);
		TaylorZeroSetting.Zero_Data_Real_Path = My_Static_Method_Lib
				.getResAbsolutePath(this, TaylorZeroSetting.Zero_Data_Path,
						false);
		if (null == TaylorZeroSetting.Zero_Data_Real_Path
				|| TaylorZeroSetting.Zero_Data_Real_Path.isEmpty()
				|| TaylorZeroSetting.Zero_Data_Real_Path.equals("")) {
			Toast.makeText(this, R.string.application_data_err,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		mContext = this;
		getDataListFromStorage(TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroStartActivitySetting.Usr_Info_Path);
		if (null != dataList && 0 < dataList.size()) {
			lstView = (ListView) findViewById(R.id.loading_list_view);
			adapter = new TaylorZeroLoadingListAdapter(mContext, dataList);
			lstView.setAdapter(adapter);
			lstView.setDividerHeight(1);
		} else {
			Toast.makeText(mContext, R.string.not_found_load_data,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
	}

	public void getDataListFromStorage(String dirPath) {
		File dirFile = new File(dirPath);
		if (dirFile.isDirectory()) {
			String[] dirList = dirFile.list();
			String parenPath = dirPath;
			if (File.separator.toCharArray()[0] != parenPath.toCharArray()[parenPath
					.length() - 1]) {
				parenPath += File.separator;
			}
			for (int i = 0; null != dirList && i < dirList.length; i++) {
				File tmpFile = new File(parenPath + dirList[i]);
				if (!tmpFile.isDirectory()
						&& 0 <= dirList[i]
								.indexOf(TaylorZeroStartActivitySetting.Usr_Info_Extend_Name)) {
					TaylorZeroLoadingOneData tmpOneData = new TaylorZeroLoadingOneData();
					tmpOneData.absolutePath = parenPath + dirList[i];
					tmpOneData.loadingItemTitle = dirList[i];
					dataList.add(tmpOneData);
				} else if (tmpFile.isDirectory()) {
					getDataListFromStorage(parenPath + dirList[i]);
				}
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

	private void getAllUsrInfo(String[] usrSettingList) {
		for (int i = 0; null != usrSettingList && i < usrSettingList.length; i++) {

		}
	}

}
