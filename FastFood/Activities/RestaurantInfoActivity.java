/**
 * 
 * Rating Activity
 * 
 */

package com.MOS.fastfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.appcompat.R.color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RestaurantInfoActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_info);


		ViewGroup  textLayout = (ViewGroup) findViewById(R.id.ll);



		TextView name_TV = (TextView) findViewById(R.id.nameTV);
		TextView address_TV = (TextView) findViewById(R.id.addressTV);
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		System.out.println("im here 222");
		try{

			name_TV.setText(RestaurantData.name.get(RestaurantData.restaurantNumber));
			address_TV.setText(RestaurantData.address.get(RestaurantData.restaurantNumber));
			ratingBar.setRating((float) Double.parseDouble(RestaurantData.rate.get(RestaurantData.restaurantNumber)));

			//adding comments inside the bottom linear layout
			
		
			System.out.println("size = " + RestaurantData.comment.size());
			for(int i =0; i < RestaurantData.comment.size(); i++)
			{
				LayoutParams tvParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				tvParams.topMargin = 10;
				final TextView tv = new TextView(this);
				tv.setText("Person Said : " + RestaurantData.comment.get(i));
				textLayout.addView(tv,tvParams);
			}
		}
		catch(Exception e)
		{
			System.out.println("ResturantNumber = " + RestaurantData.restaurantNumber);
		}
		RestaurantData.comment.clear();

	}
	

}
