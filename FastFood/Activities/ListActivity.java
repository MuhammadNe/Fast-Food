/**
 * 
 * Restaurant list activity
 * 
 */

package com.MOS.fastfood;

import java.net.URI;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.appcompat.R.color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {


	private String[] arraySpinner; //array spinner for filtering between distance and rating
	private SeekBar distanceBar; // choosing the distance
	private TextView distanceDisplay; // displaying distance for testing
	int p; // progress for SeekBar
	String toastDistance; // Toasing the distance
	CallbackManager callbackManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		
		LinearLayout innerLinearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		distanceBar = (SeekBar) findViewById(R.id.distanceBar);
		distanceDisplay = (TextView) findViewById(R.id.distanceDisplay);

		this.arraySpinner = new String[]{ "Rate", "Distance"}; //defining rate and distance
		Spinner s = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arraySpinner);
		s.setAdapter(adapter); // defining spinner with an array list

		//***********************************************************//


		// methode for changing the distance bar
		distanceBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser)
			{
				p = progress;

			}



			//on stop tracking toast the distance
			public void onStopTrackingTouch(SeekBar seekBar) 
			{
				toastDistance = p + " / " + seekBar.getMax() + " miles";

				if (seekBar.getProgress() == 0)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();
				}
				if (seekBar.getProgress() == 1)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 2)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 3)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 4)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 5)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 6)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
				if (seekBar.getProgress() == 7)
				{
					Toast.makeText(getApplicationContext(), toastDistance, Toast.LENGTH_SHORT).show();

				}
			}

			public void onStartTrackingTouch(SeekBar seekBar) 
			{

			}
		});

		//****Adding Buttons To Inner LinearLayout, Buttons Will Be Added According To Number Of Restaurants In RestaurantData.class
		for(int i =0; i < RestaurantData.name.size(); i++)
		{

			//dynamically creating linear layouts
			final LinearLayout ll = new LinearLayout(this);
			LayoutParams llParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			llParams.topMargin = 10;
			llParams.height = 170;
			ll.setBackgroundResource(R.color.reslist_color);
 			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setWeightSum(6f);
			ll.setLayoutParams(llParams);
			ll.setAlpha((float) 0.8);

			//dynamically creating buttons
			final TextView tv = new TextView(this);
			tv.setHeight(100);
			
			//tv.setBackgroundColor(Color.GRAY);
			tv.setText(RestaurantData.name.get(i));
			tv.setTextSize(20);
			
			final ImageView iv = new ImageView(this);
			System.out.println("rating = " + RestaurantData.rate.get(i));
			switch (RestaurantData.rate.get(i)) {
			case "0":
				iv.setBackgroundResource(R.drawable.newbigstar00);
				break;
			case "0.5":
				iv.setBackgroundResource(R.drawable.newbigstar01);
				break;
			case "1.0":
				iv.setBackgroundResource(R.drawable.newbigstar10);
				break;
			case "1.5":
				iv.setBackgroundResource(R.drawable.newbigstar11);
				break;
			case "2.0":
				iv.setBackgroundResource(R.drawable.newbigstar20);
				break;
			case "2.5":
				iv.setBackgroundResource(R.drawable.newbigstar21);
				break;
			case "3.0":
				iv.setBackgroundResource(R.drawable.newbigstar30);
				break;
			case "3.5":
				iv.setBackgroundResource(R.drawable.newbigstar31);
				break;
			case "4.0":
				iv.setBackgroundResource(R.drawable.newbigstar40);
				break;
			case "4.5":
				iv.setBackgroundResource(R.drawable.newbigstar41);
				break;
			case "5.0":
				iv.setBackgroundResource(R.drawable.newbigstar5);
				break;
			}
			//iv.setBackgroundResource(R.drawable.bigstar21);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.LEFT;
			
			//adding TextViews inside the Sub layout, adding the whole inside the Super layout
			innerLinearLayout.addView(ll, llParams);
			ll.addView(tv);
			ll.addView(iv, lp);
			
			ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try
					{
						RestaurantData.setName = tv.getText().toString();

						//** Fetch restaurant data from arraylists
						for(int i = 0; i < RestaurantData.name.size(); i++)
						{
							if(RestaurantData.setName.equals(RestaurantData.name.get(i)))
							{
								RestaurantData.restaurantNumber = i;
							}
						}
						
						HttpGetConnection getRequest = new HttpGetConnection(ListActivity.this);
						URI uriName;
						uriName = new URI(RestaurantData.name.get(RestaurantData.restaurantNumber).replace(" ", "%20"));
						getRequest.executeGet("http://jce-fastfood-project.appspot.com/?togo=givemecomments&name=" + uriName);
						
						System.out.println("im here 111");
						
						

					}
					catch(Exception e)
					{
						System.out.println("Can't Go To Restaurant Page");
					}

					
				}
			});

			
			
			/*
			final Button b = new Button(this);
			b.setText(RestaurantData.name.get(i));
			b.setHeight(200);
			b.setBackgroundColor(Color.parseColor("#00BFFF"));
			
			LinearLayout.LayoutParams buttonLayoutParams = new  LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			//buttonLayoutParams.setMargins(50, 10, 0, 0);
			
			buttonLayoutParams.topMargin = 5;
			b.setLayoutParams(buttonLayoutParams);
			innerLinearLayout.addView(b, buttonLayoutParams);
			b.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					try
					{
						RestaurantData.setName = b.getText().toString();

						//** Fetch restaurant data from arraylists
						for(int i = 0; i < RestaurantData.name.size(); i++)
						{
							if(RestaurantData.setName.equals(RestaurantData.name.get(i)))
							{
								RestaurantData.restaurantNumber = i;
							}
						}
						Intent intent = new Intent(getApplicationContext(), RestaurantInfoActivity.class);
						startActivity(intent);
						System.out.println("im here 111");

					}
					catch(Exception e)
					{
						System.out.println("Can't Go To Restaurant Page");
					}

				}
			});*/
		}

	}
	
	


	//button for going to a map
	public void gotoMap(View v)
	{
		

		Intent intent = new Intent(getApplicationContext(), MapActivity.class);
		startActivity(intent);
	}



}
