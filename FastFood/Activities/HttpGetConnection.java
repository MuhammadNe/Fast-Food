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

import com.facebook.FacebookActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpGetConnection
{

	private RestaurantData restaurantData = new RestaurantData();
	public static String tempResult = "";
	private ProgressDialog dialog;
	private Context context = null;

	private static boolean listActivity = false;

	public static boolean start_activity = false;
	public static boolean share = false;


	public HttpGetConnection()
	{

	}

	public HttpGetConnection(MainActivity context) 
	{
		this.context = context;
	}

	public HttpGetConnection(AddRestaurantActivity context) 
	{
		this.context = context;
	}

	public HttpGetConnection(ListActivity context) 
	{
		this.context = context;
		listActivity = true;
	}

	public void executeGet(String URL)
	{
		new Execute().execute(URL);
	}

	public  class Execute extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			System.out.println("pre execute");

			if(start_activity)
			{
				dialog = new ProgressDialog(context);
				dialog.setMessage("Fetching Data..");
				//dialog.show();
			}
		}
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
			System.out.println("im here3");
			System.out.println("responseString = " + responseString);
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println(result);
			
			//get the first 2 characters to check if i get all comments
			String tempChar1 = "", tempChar2 = "";
			tempChar1 += Character.toString(result.charAt(0));
			tempChar2 += Character.toString(result.charAt(1));

			// GET ALL COMMENTS OF A PARTICULAR RESTAURANT
			if(tempChar1.equals("1") && tempChar2.equals("#"))
			{
				restaurantData.commentParse(result);

			}
			
			// COMMENT ADDED
			else if(result.equals("commentok"))
			{
				System.out.println("comment added");
			}

			//RATE ADDED
			else if(result.equals("rateok"))
			{
				System.out.println("rating added");
			}
			//RESTAURANT ADDED
			else if(result.equals("added"))
			{
				System.out.println("Restaurant Added");
			}
			//NO COMMENTS AVAILABLE FOR A CERTAIN RESTAURANTS
			else if(result.equals("nocomments"))
			{
				System.out.println("no comments");
			}
			//GET ALL RESTAURANTS
			else if(result.equals("norestaurants"))
			{
				System.out.println("no restaurants");
			}
			//GET ALL RESTAURANTS AND PARSE THEM
			else
			{
				System.out.println("result = " + result);
				restaurantData.restaurantParse(result);
			}
			System.out.println(result);

			//check is start_activity is true : navigate to List OR Photo
			if(start_activity)
			{
				//dialog.dismiss();
				start_activity = false;
				if(!share)
				{
					Intent intent = new Intent(context, ListActivity.class);
					context.startActivity(intent);
					((Activity)context).finish();
				}
				else
				{
					share = false;
					Intent intent = new Intent(context, PhotoShare.class);
					context.startActivity(intent);
					((Activity)context).finish();
				}
			}
			
			//navigate from list activity for restaurant info when a restaurant is clicked
			if(listActivity)
			{
				listActivity = false;
				Intent intent = new Intent(context, RestaurantInfoActivity.class);
				context.startActivity(intent);
			}
		}
	}

}

