package com.adam.app.handlerthreaddemo;

import android.util.Log;

public final class Utils {
	
	public static final String KEY_VALUE = "input_num"; 
	
	private static final String TAG = "Demo";
	
	public static void print(Object obj, String str) {
		Log.i(TAG, obj.getClass().getSimpleName() + " " + str);
	}
	
	public static void print(String str) {
		Log.i(TAG, str);
	}

}