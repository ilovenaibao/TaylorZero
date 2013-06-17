package com.android.taylorzero;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.android.taylorzero.TaylorZeroPlayBgMp3CallBack.Caller;

public class TaylorZeroPlayBgMp3Service extends Service {
	// Debug tag
	private String DebugTag = "TaylorZeroPlayBgMp3Service ->";

	public boolean isFinishFlag = false;
	// 用于保存本服务自己的消息循环对象Looper
	private Looper mServiceLooper;
	// 用于保存内部类ServiceHandler的对象实例，它继承了Android的Handler类,
	// 用于处理发送给服务的消息。
	private ServiceHandler mServiceHandler;
	private long searchTime = 0;

	public String pkg_name;
	public Caller caller = null;
	private Context bindServiceContext = null;

	/**
	 * 
	 * 这个类用于给客户端提供绑定对象，因为本示例的服务与客户端运行在同一个
	 * 
	 * 主进程中，所以不需要处理进程间通信（IPC）
	 */

	public class LocalBinder extends Binder {
		TaylorZeroPlayBgMp3Service getService() {
			// 返回本服务的实例。
			return TaylorZeroPlayBgMp3Service.this;
		}
	}

	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	/**
	 * 
	 * 服务被首次创建时，系统调用这个方法。
	 * 
	 * Android服务组件必须覆写这个方法
	 */
	@Override
	public void onCreate() {
		isFinishFlag = false;
		// 创建线程对象，并启动线程。
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		// 获取线程的消息循环对象
		mServiceLooper = thread.getLooper();
		// 用线程的消息循环对象创建消息处理对象。
		mServiceHandler = new ServiceHandler(mServiceLooper);
		mServiceHandler.sendEmptyMessage(0);
		searchTime = System.currentTimeMillis();
	}

	/**
	 * 
	 * 绑定并启动服务，bind按钮点击时会调用这个方法。
	 */

	public void doBindService(Context context) {
		// 绑定并启动服务。
		bindServiceContext = context;
		boolean bindFlag = false;
		bindFlag = context.bindService(new Intent(context,
				TaylorZeroPlayBgMp3Service.class), mConnection,
				Context.BIND_AUTO_CREATE);
		int a = 0;
		a = 1;
		// mIsBound = true;

	}

	public ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// 清除客户端服务实例
			int a = 0;
			a = 1;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 从IBinder对象中获取服务实例。
			int a = 0;
			a = 1;
		}
	};

	/**
	 * 
	 * 解除与服务的绑定，unbind按钮被点击时会调用这个方法
	 */
	public void doUnbindService() {
		// 如果服务被绑定，则解除与服务绑定。
		try {
			bindServiceContext.unbindService(mConnection);
			bindServiceContext = null;
		} catch (Exception e) {

		}

	}

	/**
	 * 
	 * 必须覆写这个方法，服务被终止时要调用这个方法，清理服务所占用的资源。
	 */
	@Override
	public void onDestroy() {
		// 退出服务线程的消息循环。
		mServiceLooper.quit();
		// 显示服务被退出的提示信息。
	}

	/**
	 * 
	 * 该类继承Android的Handler类，为线程的消息循环提供发送和处理消息的功能，
	 * 
	 * 本示例覆写了handleMessage()方法，用来处理发送给服务消息循环的消息。
	 */
	private final class ServiceHandler extends Handler {
		// 类实例化时，需要传入服务线程的消息循环对象
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		/**
		 * 
		 * 覆写Handler类的handleMessage()方法，当服务线程的消息循环接收到外部
		 * 
		 * 发送的消息时，会调用这个方法来处理对应的消息，本示例只是简单的向用户提示消息被处理的信息。
		 */

		public void handleMessage(Message msg) {
			int count = 1;
			while (!isFinishFlag) {
				// Toast.makeText(LocalService.this, "" + count,
				// Toast.LENGTH_SHORT).show();
				if (System.currentTimeMillis() - searchTime > 120000) {
					Log.i(DebugTag, "timer--> 120000");
					Message msg1 = new Message();
					// msg1.what =
					// BA014_bestaFunMathCourseActivity.DOWNLOADFLASH_STATE_CHANGED;
					// BA014_bestaFunMathCourseActivity window =
					// (BA014_bestaFunMathCourseActivity)
					// BA014_bestaFunMathCourseActivity.mainContext;
					// window.superHandler.sendMessage(msg1);
				}
			}
		}
	}
}
