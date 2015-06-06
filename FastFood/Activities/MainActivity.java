/*
 * This is a simple Android mobile client
 * This application read any string messege typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends Activity {

	Button list,get;
	String URL = "http://jce-fastfood-project.appspot.com";
	String tempResult ="";
	
	RestaurantData restaurantData = new RestaurantData();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (Button) findViewById(R.id.save);
		get = (Button) findViewById(R.id.get);
		final TextView tv = (TextView) findViewById(R.id.showData);
		
		//GET button for fetching all restaurants list
		get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new RequestTask().execute("http://jce-fastfood-project.appspot.com/?togo=restfunctions");
				
				//tv.append(RestaurantData.Data.size()+"");
				
				/*for(int i =0; i < RestaurantData.Data.size(); i++)
				{
					tv.append(RestaurantData.Data.get(i) + "--\n");

				}*/
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
	
	/***********************************Sending HTTP GET REQUES****************************************************/

	
	
	class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			try {
				response = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
				} else{
					//Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tempResult = result;
			//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			//restaurantData.parse(result);
			//RestaurantData.Data.add(result);
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}
	}
	


}

