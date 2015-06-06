package com.MOS.fastfood;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//import com.MOS.fastfood.MainActivity.Loader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
/*import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.Plus;
import android.test.PerformanceTestCase;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 */
import com.google.android.gms.maps.model.MarkerOptions;



//implementing google api client to connect and get current location
public class MapActivity extends FragmentActivity implements
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;


	//Declaring Google Map
	private GoogleMap mMap;
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;

	//Getting lng AND lat
	RestaurantData restaurantData = new RestaurantData();
	String tempResult;
	String lng = "", lat = "";

	//RestaurantData data = new RestaurantData();

	public static final String TAG = MapActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * To check the google play services APK is available on the device
		 */
		if(servicesOK())
		{
			setContentView(R.layout.activity_map);

			// load the map only if the map object is available to use!
			initMap();

			Toast.makeText(this, "Map Ready!", Toast.LENGTH_LONG).show();
			//gotoLocation(beitHanina_LAT, beitHanina_LNG);
			//gotoLocation(beitHanina_LAT, beitHanina_LNG, defaultZoom);

			//mMap.setMyLocationEnabled(true); // Program Changed To Fused
			//Toast.makeText(this, lat + "," + lng, Toast.LENGTH_LONG).show();
			/*LatLng = Double.toString(lat) + "," + Double.toString(lng);
				Toast.makeText(this, LatLng, Toast.LENGTH_SHORT).show();*/



			//gotoLocation(lat, lng, defaultZoom);

			//mGoogleApiClient = new GoogleApiClient.Builder(this, this, this);
			//mGoogleApiClient.build();


			mGoogleApiClient = new GoogleApiClient.Builder(this)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.addApi(LocationServices.API)
			.build();

			/*mLocationRequest = LocationRequest.create()
					.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
					.setInterval(5000)
					.setFastestInterval(1000);	*/

			EditText et = (EditText) findViewById(R.id.editText1);
			Button locationButton = (Button) findViewById(R.id.LocationButton);

			locationButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					if((lat.equals(null) || lng.equals(null)) || (lat.equals("") || lng.equals("")))
					{
						Toast.makeText(getApplicationContext(), "Can't Get Coordinates",Toast.LENGTH_SHORT).show();
					}
					else
					{
						restaurantData.saveLat = lat;
						restaurantData.savelng = lng;
						/*ShowLocation();*/
						Intent intent = new Intent(getApplicationContext(), AddRestaurantActivity.class);
						startActivity(intent);
						finish();
					}
				}
			});

			et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						try {
							geoLocate(v);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}
					return false;
				}

			});



		}
		else
		{
			Toast.makeText(this, "Can't Load Map.", Toast.LENGTH_LONG).show();
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		initMap();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mGoogleApiClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
			mGoogleApiClient.disconnect();

		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	/*
	 * THIS METHOD CHECKS IF MOBILE SUPPORT GOOGLE SERVICES, CHECK MANIFEST META-DATA FOR MORE INFO
	 * 
	 */
	public boolean servicesOK()
	{
		int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if(isAvailable == ConnectionResult.SUCCESS)
		{
			return true;
		}
		else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable))
		{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		}
		else
		{
			Toast.makeText(this, "Can't Connect To Google Play Services", Toast.LENGTH_LONG).show();
		}
		return false;
	}

	/*
	 * Check if the map object is available to use
	 */
	private void initMap()
	{
		if(mMap == null)
		{
			SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
		}
		else
		{
			//Toast
		}
	}
	/*
	 * Initialize the map with a specific location
	 */
	private void gotoLocation(double lat, double lng, float zoom)
	{
		LatLng ll = new LatLng(lat,lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.animateCamera(update);
	}
	/*
	 * Initialize the map with a specific location using animated zoom
	 */
	private void gotoLocation(Location location) {
		double lat, lng;
		lat = location.getLatitude();
		lng = location.getLongitude();
		LatLng ll = new LatLng(lat,lng);
		mMap.addMarker(new MarkerOptions().position(ll).title("Marker"));
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,15);
		mMap.animateCamera(update);
	}

	private void handleNewLocation(Location location) {
		Log.d(TAG, location.toString());

		double currentLatitude = location.getLatitude();
		double currentLongitude = location.getLongitude();

		LatLng latLng = new LatLng(currentLatitude, currentLongitude);

		//mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
		MarkerOptions options = new MarkerOptions()
		.position(latLng)
		.title("I am here!");
		mMap.addMarker(options);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	}

	/*
	 * Searches for a location when press on Go button 
	 */
	public void geoLocate(View v) throws IOException {
		//hides the keyboards when activated
		hideSoftKeyboard(v);

		EditText et = (EditText) findViewById(R.id.editText1);



		String location = et.getText().toString();



		if(location.length() == 0)
		{
			Toast.makeText(this, "Blank Location!", Toast.LENGTH_SHORT).show();
			return;
		}


		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location,1);
		Address add = list.get(0);
		String locality = add.getLocality();
		Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

		double lat = add.getLatitude();
		double lng = add.getLongitude();

		gotoLocation(lat,lng, 15);
	}

	/*
	 * This method hides the keyboards after searching for a geo location
	 */
	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}





	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}





	@Override
	public void onConnected(Bundle bundle) {

		/* Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
	        if (location == null) {
	            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	        }
	        else {
	            handleNewLocation(location);
	        }*/

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(5000);
		mLocationRequest.setFastestInterval(1000);
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
	}





	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}





	@Override
	public void onLocationChanged(Location location) {
		gotoLocation(location);
		lng = location.getLongitude() + "";
		lat = location.getLatitude() + "";
		
		
		
		//Toast.makeText(this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
		//handleNewLocation(location);

	}


	public void ShowLocation()
	{
		Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		try
		{
			Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(this, "Null Location", Toast.LENGTH_SHORT).show();
		}
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
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			//restaurantData.parse(result);

		}
	}













}
