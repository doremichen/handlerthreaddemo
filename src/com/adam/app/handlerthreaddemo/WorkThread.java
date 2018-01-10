package com.adam.app.handlerthreaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class WorkThread extends HandlerThread {

	//Handler
	private Handler mHandler;
	
	//CallBack
	private HandlerCallBack mCallBack;
	
	public WorkThread(HandlerCallBack callBack) {
		super("MyWorkThread_Hanlder");
		mCallBack = callBack;
	}

	@Override
	protected void onLooperPrepared() {
		super.onLooperPrepared();
		Utils.print(this, "[onLooperPrepared] enter");
		mHandler = new Handler(this.getLooper()) {
			/**
			 * 
			 * Receive data from the message queue
			 *
			 * @param msg
			 */
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Utils.print(this, "[handleMessage] enter");
				Bundle data = msg.getData();
				int input_value = data.getInt(Utils.KEY_VALUE);
				Utils.print(this, "input number = " + input_value);
				
				//Handle data process
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				int result = Math.max(input_value, 2000);
				
				//Call back to UI
				mCallBack.Call(result);
			}
			
		};
	}
	

	/*
	 * Use to send data to message queue
	 */
	public void sendMessage(Message msg) {
		Utils.print(this, "[sendMessage] enter");
		mHandler.sendMessage(msg);
	}
	
	/*
	 * Use to send data to message queue
	 */
	public void sendEmptyMessage(int what) {
		Utils.print(this, "[sendEmptyMessage] enter");
		mHandler.sendEmptyMessage(what);
	}
	
	/*
	 * Use to remove data in message queue
	 */
	public void removeMessages(int what) {
		Utils.print(this, "[removeMessages] enter");
		mHandler.removeMessages(what);
	}
	
	/*
	 * Use to remove data in message queue
	 */
	public void removeMessages(int what, Object obj) {
		Utils.print(this, "[removeMessages] enter");
		mHandler.removeMessages(what, obj);
	}
	

}
