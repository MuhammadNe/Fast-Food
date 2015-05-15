/**
 * 
 * Main Activity, will be implemented
 * 
 */


package com.MOS.fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(getApplicationContext(), ListActivity.class);
		startActivity(intent);
		
		
	}
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}
