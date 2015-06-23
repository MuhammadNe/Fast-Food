

package com.MOS.fastfood;


import com.facebook.appevents.AppEventsLogger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	//******** Defining Objects
	RestaurantData restaurantData = new RestaurantData();
	ProgressDialog dialog;

	//******** Defining String
	String URL = "http://jce-fastfood-project.appspot.com/?togo=restfunctions";
	String tempResult ="";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Send HTTP GET REQUEST to the server to get all the restaurants
		HttpGetConnection getRequest = new HttpGetConnection(MainActivity.this);
		HttpGetConnection.start_activity = true;
		getRequest.executeGet(URL);


		//Intent intent = new Intent(getApplicationContext(), ListActivity.class);
		//startActivity(intent);
	}


}
