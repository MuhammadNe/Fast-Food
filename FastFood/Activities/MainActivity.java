/*
 * This is a simple Android mobile client
 * This application read any string messege typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 */

package com.MOS.fastfood;


import android.app.Activity;
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

	//******** Defining String
	String URL = "http://jce-fastfood-project.appspot.com/?togo=restfunctions";
	String tempResult ="";

	//******** Defining Buttons
	Button list,get;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HttpGetConnection getRequest = new HttpGetConnection();
		getRequest.execute(URL);
		
		list = (Button) findViewById(R.id.save);
		get = (Button) findViewById(R.id.get);
		final TextView tv = (TextView) findViewById(R.id.showData);
		
		//GET button for fetching all restaurants list
		get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//Toast.makeText(getApplicationContext(), HttpGetConnection.tempResult, Toast.LENGTH_LONG).show();
				//System.out.println(HttpGetConnection.tempResult);
				RestaurantData restaurantData = new RestaurantData();
				System.out.println(RestaurantData.rate.size() + "-" + RestaurantData.name.size());
				for(int i =0; i < RestaurantData.rate.size(); i++)
				{
					tv.append(restaurantData.rate.get(i) + "--\n");
				}
				//tv.append(RestaurantData.lng.get(RestaurantData.lng.size()));			
			}
		});
		
		//List button to move to the LIST ACTIVITY
		list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ListActivity.class);
				startActivity(intent);
				/*try {
					String query = "My name is bond, james bond";

					String urlQ = "http://jce-fastfood-project.appspot.com/" + URLEncoder.encode(query, "UTF-8");
					Toast.makeText(getApplicationContext(), urlQ, Toast.LENGTH_LONG).show();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//new RequestTask().execute("http://jce-fastfood-project.appspot.com/?lng=15");
			}
		});
	}
	
}
