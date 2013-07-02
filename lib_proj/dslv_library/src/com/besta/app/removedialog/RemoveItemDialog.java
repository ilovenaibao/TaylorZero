package com.besta.app.removedialog;

import com.mobeta.android.dslv.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class RemoveItemDialog {
	Activity parentActivity = null;
	Context parentContext = null;
	public Dialog dlg = null;
	public Button selectBt_ok = null;
	public Button selectBt_cancel = null;
	public boolean g_exit = false;
	public boolean exit_keydownflag = false;
	public boolean g_showExitDlg = false;

	public RemoveItemDialog(Context context) {
		parentContext = context;
		parentActivity = (Activity) parentContext;
		g_exit = false;
		exit_keydownflag = false;
		g_showExitDlg = false;
	}

	public void ShowRemoveDlg() {
		TextView exit_titleBar = null;

		WindowManager wm = (WindowManager) parentContext
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int realWidth = 220;
		// int height = wm.getDefaultDisplay().getHeight();// 屏幕高度
		// Rect rect = new Rect();
		// this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		// int statusBarHeight = rect.top; // 状态栏高度

		dlg = new Dialog(parentContext, R.style.ChooseDialogTile);
		LayoutInflater inFlater = LayoutInflater.from(parentContext);
		final View exitSelectView = inFlater.inflate(R.layout.selectversion,
				null);
		dlg.setContentView(exitSelectView);

		exit_titleBar = (TextView) exitSelectView
				.findViewById(R.id.exit_tvTitle);
		exit_titleBar.setText(parentContext.getString(R.string.exit_title));
		if (width - 20 >= realWidth) {
			exit_titleBar.setWidth(realWidth);
		} else {
			exit_titleBar.setWidth(width - 20);
		}
		exit_titleBar.setTextColor(0xF0FAFAFA);

		selectBt_ok = (Button) exitSelectView.findViewById(R.id.bt_ok);
		selectBt_ok.setText(parentContext.getString(R.string.exit_ok));
		selectBt_ok.setPadding(2, 2, 2, 2);
		selectBt_ok.setBackgroundResource(R.drawable.exit_button_up);
		selectBt_ok.setTextColor(Color.BLACK);
		// selectBt_ok.setOnClickListener(selectBt_ok_listener);
		// selectBt_ok.setOnTouchListener(bt_ok_touch_listener);

		selectBt_cancel = (Button) exitSelectView.findViewById(R.id.bt_cancel);
		selectBt_cancel.setText(parentContext.getString(R.string.exit_cancel));
		selectBt_cancel.setPadding(2, 2, 2, 2);
		selectBt_cancel.setBackgroundResource(R.drawable.exit_button_up);
		// selectBt_cancel.setOnClickListener(selectBt_cancel_listener);
		// selectBt_cancel.setOnTouchListener(bt_cancel_touch_listener);

		exit_keydownflag = false;
		// dlg.setOnKeyListener(dlgKey_listener);
		dlg.show();
	}

	private OnKeyListener dlgKey_listener = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (!exit_keydownflag) {
					exit_keydownflag = true;
				} else {
					exit_keydownflag = false;
					selectBt_cancel
							.setBackgroundResource(R.drawable.exit_button_down);
					g_exit = false;
					g_showExitDlg = false;
					dlg.dismiss();
				}

			} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
				selectBt_ok.setBackgroundResource(R.drawable.exit_button_down);
				g_exit = true;
				g_showExitDlg = true;
				dlg.dismiss();
				parentActivity.finish();
			}
			Log.d("dlgKey_listener========", "" + exit_keydownflag);
			return false;
		}
	};

	private Button.OnTouchListener bt_ok_touch_listener = new Button.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				selectBt_ok.setBackgroundResource(R.drawable.exit_button_down);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				selectBt_ok.setBackgroundResource(R.drawable.exit_button_up);
			}

			return false;
		}

	};

	private Button.OnTouchListener bt_cancel_touch_listener = new Button.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				selectBt_cancel
						.setBackgroundResource(R.drawable.exit_button_down);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				selectBt_cancel
						.setBackgroundResource(R.drawable.exit_button_up);
			}
			return false;
		}

	};

	private Button.OnClickListener selectBt_ok_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			g_exit = true;
			g_showExitDlg = true;
			dlg.dismiss();
			parentActivity.finish();
		}

	};

	private Button.OnClickListener selectBt_cancel_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			g_exit = false;
			g_showExitDlg = false;
			dlg.dismiss();
		}
	};
}
