/**
 * 
 * Adding a restaurant with review activity
 * 
 */

package com.MOS.fastfood;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;



import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.URIUtils;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddRestaurantActivity extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_restaurant);

	}


	//submitting the review
	public void gotoSubmit(View v)
	{
		showDialog();

	}

	//this method submits all data of a new restaurant
	private void submitData() throws URISyntaxException
	{
		HttpGetConnection getRequest = new HttpGetConnection(AddRestaurantActivity.this);	

		EditText nameED = (EditText) findViewById(R.id.resName);
		EditText commentED = (EditText) findViewById(R.id.resComment);
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);

		URI uriName, uriComment;

		float tempRate = 0;

		if(nameED.getText().toString().trim().length() > 0)
		{
			RestaurantData.saveName += nameED.getText();
			tempRate = ratingBar.getRating();
			RestaurantData.saveRate = Float.toString(tempRate);
			
			uriName = new URI(RestaurantData.saveName.replace(" ", "%20"));
			
			getRequest.executeGet("http://jce-fastfood-project.appspot.com/?togo=new_rest&name=" + uriName + "&address=Mushroom_Land" +  "&lng=" + RestaurantData.savelng + "&lat=" + RestaurantData.saveLat);

			System.out.println(RestaurantData.saveRate);

			if(commentED.getText().toString().trim().length() > 0)
			{
				RestaurantData.saveComment += commentED.getText();
				uriComment = new URI(RestaurantData.saveComment.replace(" ", "%20"));
				System.out.println(uriComment);

				getRequest.executeGet("http://jce-fastfood-project.appspot.com/?togo=commentit&name=" + uriName + "&comment=" + uriComment);
				RestaurantData.saveComment = "";

			}
			
			System.out.println("rating is = " + RestaurantData.saveRate);
			getRequest.executeGet("http://jce-fastfood-project.appspot.com/?togo=rateit&name=" + uriName + "&rat=" + tempRate);

			HttpGetConnection.start_activity = true;
			
			//added!!!!!!!!!!!!!!!!!!
			getRequest.executeGet("http://jce-fastfood-project.appspot.com/?togo=restfunctions");
			RestaurantData.saveName = "";
			RestaurantData.saveAddress = "";
			RestaurantData.saveRate = "";



		}
		else
		{
			Toast.makeText(getApplicationContext(), "Please Provide A Name.", Toast.LENGTH_SHORT).show();
		}
	}


	// Showing a dialog that asks to share a picture on facebook
	private void showDialog() 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Share On Facebook")
		.setMessage("Would you like to share a picture on facebook?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				HttpGetConnection.share = true;
				dialog.cancel();
				
				//TODO add a new dialog to upload a picture to facebook
				
				try {
					submitData();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				try {
					submitData();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
