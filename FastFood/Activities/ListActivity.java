/**
 * 
 * Restaurant list activity
 * 
 */

package com.MOS.fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends ActionBarActivity {


	private String[] arraySpinner; //array spinner for filtering between distance and rating
	private SeekBar distanceBar; // choosing the distance
	private TextView distanceDisplay; // displaying distance for testing
	int p; // progress for SeekBar
	String toastDistance; // Toasing the distance

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

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
			final Button b = new Button(this);
			b.setText(RestaurantData.name.get(i));
			b.setHeight(200);
			innerLinearLayout.addView(b);
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
						Intent intent = new Intent(getApplicationContext(), RateActivity.class);
						startActivity(intent);
						System.out.println("im here 111");

					}
					catch(Exception e)
					{
						System.out.println("Can't Go To Restaurant Page");
					}

				}
			});
		}

	}

	//button for going to restaurant
	public void gotoRestaurant(View v)
	{
		Intent intent = new Intent(getApplicationContext(), RateActivity.class);
		startActivity(intent);
	}

	//button for going to a map
	public void gotoMap(View v)
	{
		Intent intent = new Intent(getApplicationContext(), MapActivity.class);
		startActivity(intent);
	}



}
