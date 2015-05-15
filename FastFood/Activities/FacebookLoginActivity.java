/**
 * 
 * Facebook login activity, Will be implemented
 * 
 */

package com.MOS.fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class FacebookLoginActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_login);
	}
	
	//button for adding a retaurant
	public void gotoAddRestaurant(View v)
	{
		Intent intent = new Intent(getApplicationContext(), AddRestaurantActivity.class);
		startActivity(intent);
		finish();
	}
}
