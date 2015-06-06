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

import com.MOS.fastfood.MainActivity.RequestTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
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
		EditText nameED = (EditText) findViewById(R.id.editText1);
		String tempName;
		tempName = nameED.getText().toString();
		if(nameED.getText().toString().trim().length() > 0)
		{
			RestaurantData.saveName += nameED.getText();
			new RequestTask().execute("http://jce-fastfood-project.appspot.com/?newrest=new_rest&name=" + RestaurantData.saveName + "&address=inhell&lng=" + RestaurantData.savelng + "&lat=" + RestaurantData.saveLat);
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
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
		}
	}
	
	
}
