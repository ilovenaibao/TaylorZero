package com.android.taylorzero.login.start;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.mylib.dbtypeconvert.MyDBTypeConvert;
import com.android.mylib.screen.MyLibScreenInfo;
import com.android.mylib.screen.MyLibScreenSetting;
import com.android.mylib.staticmethod.My_Static_Method_Lib;
import com.android.taylorzero.R;
import com.android.taylorzero.TaylorZeroDataControl;
import com.android.taylorzero.setting.TaylorZeroSetting;
import com.android.taylorzero.setting.TaylorZeroStartActivitySetting;

public class TaylorZeroStartActivity extends Activity {

	public final static int MSG_HANDLER_EDT = 0x0001;

	Context mContext = null;
	MyLibScreenInfo scrInfo = null;
	EditText inputNewer = null;
	private String edtextStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyLibScreenSetting.SettingScreenShowTheme(this,
				MyLibScreenSetting.SCREEN_SHOW_THEME_FULL_SCREEN);
		MyLibScreenSetting.SettingScreenHorizontal(this);
		setContentView(R.layout.taylorzero_activity_newstart);
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
		scrInfo = MyLibScreenSetting.GetScreenSize(this, 1);
		// setting input edit layout
		inputNewer = (EditText) findViewById(R.id.input_newer_ed);
		ViewGroup.LayoutParams lp = inputNewer.getLayoutParams();
		lp.width = scrInfo.scrWidth / 2;
		inputNewer.setLayoutParams(lp);
		inputNewer.setHighlightColor(0xAADD80AA);
		ImageButton btOk = (ImageButton) findViewById(R.id.bt_ok);
		ImageButton btCancel = (ImageButton) findViewById(R.id.bt_cancel);
		btOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editable mEditable = inputNewer.getText();
				String userName = mEditable.toString();
				if (null == userName) {
					userName = "";
				}
				compeleteEnterNewUsr(userName);
				int i = 0;
				i = 1;
			}
		});
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity) mContext).finish();
			}
		});
		inputNewer.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_ENTER:
					String userName = v.getText().toString();
					if (null == userName) {
						userName = "";
					}
					compeleteEnterNewUsr(userName);
					break;
				}
				return false;
			}
		});
	}

	private void compeleteEnterNewUsr(String userName) {
		byte[] userNameBytes = userName.getBytes();
		String usernameID = TaylorZeroDataControl.bytes2String(userNameBytes);
		String absolutePath = TaylorZeroSetting.Zero_Data_Real_Path
				+ TaylorZeroStartActivitySetting.Usr_Info_Path;
		String fullNameID = absolutePath + usernameID;
		if (TaylorZeroStartActivitySetting.MAX_USER_ID_LEN > fullNameID
				.length()) {
			if (null == usernameID || usernameID.isEmpty()) {
				Toast.makeText(mContext, R.string.user_id_len_null,
						Toast.LENGTH_SHORT).show();
			} else {
				// create this user ID
				if (createUserInfo(absolutePath, usernameID, userName)) {
					Toast.makeText(mContext, R.string.create_user_success,
							Toast.LENGTH_SHORT).show();
					((Activity) mContext).finish();
				}
			}
		} else {
			// user ID error
			Toast.makeText(mContext, R.string.user_id_len_err,
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean createUserInfo(String path, String userID, String usrName) {
		boolean ret = false;
		String realPath = path;
		String usrInfoFilePath = path + userID + File.separator;
		String usrInfoFileName = userID
				+ TaylorZeroStartActivitySetting.Usr_Info_Extend_Name;
		if (File.separator.toCharArray()[0] == realPath.toCharArray()[realPath
				.length() - 1]) {
			realPath = realPath.substring(0, realPath.length() - 1);
		}
		File dir = new File(path + userID);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
			File tmpFile = new File(usrInfoFilePath + usrInfoFileName);
			if (!tmpFile.exists()
					&& (null == dir.list() || 0 == dir.list().length)) {

			} else {
				Toast.makeText(mContext, R.string.cannot_create_usr,
						Toast.LENGTH_SHORT).show();
			}
		}
		if (dir.exists() && dir.isDirectory()) {
			dir = new File(usrInfoFilePath + usrInfoFileName);
			if (!dir.exists()) {
				try {
					dir.createNewFile();
					RandomAccessFile rf = new RandomAccessFile(usrInfoFilePath
							+ usrInfoFileName, "rw");
					// write mark head
					rf.write(TaylorZeroDataControl.mark_head);
					// write data str len
					int strLen = usrName.length();
					byte[] strLenBytes = MyDBTypeConvert.int2bytes4(strLen, 0);
					strLenBytes = MyDBTypeConvert.int2bytes4(strLen, 1);
					rf.write(strLenBytes);
					// write data str
					rf.writeChars(usrName);
					// write mark end
					rf.write(TaylorZeroDataControl.mark_end);
					rf.close();
					ret = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_HANDLER_EDT:
				inputNewer.setText(edtextStr);
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MyLibScreenSetting.SettingScreenHorizontal(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
