package com.MOS.fastfood;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.Plus;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.test.PerformanceTestCase;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//implementing google api client to connect and get current location
public class MainActivity extends FragmentActivity implements
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	//Declaring Google Map
	GoogleMap mMap;
	private static final double beitHanina_LNG = 35.222458, beitHanina_LAT = 31.826439;
	private static float defaultZoom = 10;


	GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		double lng ;
		double lat ;
		String LatLng  = null;



		/*
		 * To check the google play services APK is available on the device
		 */
		if(servicesOK())
		{
			setContentView(R.layout.activity_map);

			// load the map only if the map object is available to use!
			if(initMap())
			{
				Toast.makeText(this, "Map Ready!", Toast.LENGTH_LONG).show();
				//gotoLocation(beitHanina_LAT, beitHanina_LNG);
				//gotoLocation(beitHanina_LAT, beitHanina_LNG, defaultZoom);

				mMap.setMyLocationEnabled(true);
				//Toast.makeText(this, lat + "," + lng, Toast.LENGTH_LONG).show();
				/*LatLng = Double.toString(lat) + "," + Double.toString(lng);
				Toast.makeText(this, LatLng, Toast.LENGTH_SHORT).show();*/



				//gotoLocation(lat, lng, defaultZoom);

				//mGoogleApiClient = new GoogleApiClient.Builder(this, this, this);
				//mGoogleApiClient.build();


				/*mGoogleApiClient = new GoogleApiClient.Builder(this)
		        .addConnectionCallbacks(this)
		                .addOnConnectionFailedListener(this)
		                .addApi(Plus.API, null)
		                .addScope(Plus.SCOPE_PLUS_LOGIN)
		                .build();*/

				EditText et = (EditText) findViewById(R.id.editText1);
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
		else
		{
			setContentView(R.layout.activity_main);
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
	private boolean initMap()
	{
		if(mMap == null)
		{
			SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
		}
		return (mMap != null);
	}
	/*
	 * Initialize the map with a specific location
	 */
	private void gotoLocation(double lat, double lng)
	{
		LatLng ll = new LatLng(lat,lng);
		CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
		mMap.moveCamera(update);
	}
	/*
	 * Initialize the map with a specific location using animated zoom
	 */
	private void gotoLocation(double lat, double lng, float zoom) {
		LatLng ll = new LatLng(lat,lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
		mMap.animateCamera(update);
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

		gotoLocation(lat, lng,defaultZoom);
	}

	/*
	 * This method hides the keyboards after searching for a geo location
	 */
	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}


	/*
	 * 
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Toast.makeText(this, "Can't Connect",Toast.LENGTH_SHORT).show();		
	}



	@Override
	public void onConnected(Bundle arg0) {

		Toast.makeText(this, "Connected",Toast.LENGTH_SHORT).show();

	}



	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

}
