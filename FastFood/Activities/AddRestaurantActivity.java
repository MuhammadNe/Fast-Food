/**
 * 
 * Adding a restaurant with review activity
 * 
 */

package com.MOS.fastfood;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddRestaurantActivity extends ActionBarActivity {


	//EditText nameED = (EditText) findViewById(R.id.editText1);
	
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
	
	//submitting the review
	public void gotoSubmit(View v)
	{
		HttpGetConnection getRequest = new HttpGetConnection();
		float tempRate;
		EditText nameED = (EditText) findViewById(R.id.editText1);
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		if(nameED.getText().toString().trim().length() > 0)
		{
			RestaurantData.saveName += nameED.getText();
			tempRate = ratingBar.getRating();
			RestaurantData.saveRate = Float.toString(tempRate);
			getRequest.execute("http://jce-fastfood-project.appspot.com/?newrest=new_rest&name=" + RestaurantData.saveName + "&address=inhell&lng=" + RestaurantData.savelng + "&lat=" + RestaurantData.saveLat);
			//getRequest.execute("http://jce-fastfood-project.appspot.com/?togo=rateit&name=" + RestaurantData.saveName + "&rat=" + RestaurantData.saveRate);
			RestaurantData.saveName = "";
			Intent intent = new Intent(getApplicationContext(), ListActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Please Provide A Name.", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	
	
}
