package com.adam.app.handlerthreaddemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements HandlerCallBack{

	//Input edit
	private EditText mInput;
	
	//Show result
	private TextView mResult;
	
	//Handler thread
	private WorkThread mWork;
	
	//Progress dialog
	private ProgressDialog mProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Utils.print(this, "[onCreate] enter");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mInput = (EditText)this.findViewById(R.id.input);
		mResult = (TextView)this.findViewById(R.id.showResult);
		
		//Start handler thread
		mWork = new WorkThread(this);
		mWork.start();
		
		mProgress = new ProgressDialog(this);
		mProgress.setMessage("Waiting...");
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
	}

	
	
	@Override
	protected void onDestroy() {
		Utils.print(this, "[onDestroy] enter");
		super.onDestroy();
		//Stop handler thread
		mWork.quit();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onStartThread(View v) {

		//Hide soft keyboard
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		//Get value from input
		String value = mInput.getText().toString();
		Utils.print(this, "[onStartThread] value = " + value);
		
		if (value.equals("")) {
			Toast.makeText(this, "Please input the valid number.", Toast.LENGTH_SHORT).show();
		} else {
			//handle data
			Message msg = new Message();
			//Prepare data
			Bundle bundle = new Bundle();
			bundle.putInt(Utils.KEY_VALUE, Integer.valueOf(value));
			msg.setData(bundle);
			//Send message
			mWork.sendMessage(msg);
			
			//Show progress
			mProgress.show();
		}
		
		//clear input
		mInput.setText("");
	}

	@Override
	public void Call(int value) {
		Utils.print(this, "[Call] enter");
		final int show_value = value;
		
		//Show result
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				mResult.setText(String.valueOf(show_value));
				
				//Dismiss progress
				mProgress.dismiss();
			}
			
		});
		
	}
}
