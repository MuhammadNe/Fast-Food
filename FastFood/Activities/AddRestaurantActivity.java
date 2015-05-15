/**
 * 
 * Adding a restaurant with review activity
 * 
 */

package com.MOS.fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class AddRestaurantActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_restaurant);
	}
	
	//button for going to gallery and choose a photo for submitting
	public void gotoGallery(View v)
	{
		Intent i=new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivity(i);
	}
	
	//submitting the review, will be implemented
	public void gotoSubmit(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ListActivity.class);
		startActivity(intent);
		finish();
	}
	
	
}
