/**
 * 
 * Rating Activity
 * 
 */

package com.MOS.fastfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateActivity extends ActionBarActivity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate);
		TextView name_TV = (TextView) findViewById(R.id.nameTV);
		TextView address_TV = (TextView) findViewById(R.id.addressTV);
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		System.out.println("im here 222");
		try{
			name_TV.setText(RestaurantData.name.get(RestaurantData.restaurantNumber));
			address_TV.setText(RestaurantData.address.get(RestaurantData.restaurantNumber));
			ratingBar.setRating((float) Double.parseDouble(RestaurantData.rate.get(RestaurantData.restaurantNumber)));
		}
		catch(Exception e)
		{
			System.out.println("ResturantNumber = " + RestaurantData.restaurantNumber);
		}
	}


}
