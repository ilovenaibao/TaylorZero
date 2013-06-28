package com.android.taylorzero.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.mylib.dbtypeconvert.MyDBTypeConvert;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroDataControl;
import com.android.taylorzero.setting.TaylorZeroSetting;
import com.android.taylorzero.setting.TaylorZeroStartActivitySetting;

public class TaylorZeroDataControlTest extends Activity {

	private Context mContext = null;
	public EditText inputEdit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_datacontrol_test);
		mContext = this;
		inputEdit = (EditText) findViewById(R.id.ed_view);
		Button bt_ok = (Button) findViewById(R.id.bt_ok);
		Button bt_cancel = (Button) findViewById(R.id.bt_cancel);
		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editable edtable = inputEdit.getEditableText();
				String inputStr = edtable.toString();
				inputStr = TaylorZeroDataControl.bytes2String(inputStr
						.getBytes());
				inputStr = My_Static_Method_Lib
						.getResAbsolutePath(
								mContext,
								"Zero/usr/"
										+ inputStr
										+ "/"
										+ inputStr
										+ TaylorZeroStartActivitySetting.Usr_Info_Extend_Name,
								false);
				if (null != inputStr && !inputStr.isEmpty()) {
					RandomAccessFile rf;
					try {
						rf = new RandomAccessFile(inputStr, "r");
						byte[] tmpMarkBytes = new byte[2];
						rf.read(tmpMarkBytes);
						byte[] lenBytes = new byte[4];
						rf.read(lenBytes);
						int len = MyDBTypeConvert.bytes4ToInt(lenBytes, 1) * 2;
						byte[] buffer = new byte[len];
						rf.read(buffer);
						String tmpStr = null;
						try {
							tmpStr = MyDBTypeConvert.nBytes2Str(buffer, 0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						rf.read(tmpMarkBytes);
						rf.close();
						int i = 0;
						i = 1;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

}
