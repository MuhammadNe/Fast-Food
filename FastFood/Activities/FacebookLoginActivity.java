/**
 * 
 * Facebook login activity, Will be implemented
 * 
 */

package com.MOS.fastfood;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookLoginActivity extends FragmentActivity {

	CallbackManager callbackManager;
	private String id, name;
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();

		setContentView(R.layout.activity_facebook_login);

		LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
		
		 
		//This block of code gets the hash key and displays it in the console.
            try {
                PackageInfo info = this.getPackageManager().getPackageInfo(
                        "com.MOS.fastfood", PackageManager.GET_SIGNATURES);
                for (android.content.pm.Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    }
            } catch (NameNotFoundException e) {
            	System.out.println("bullshit1");
            } catch (NoSuchAlgorithmException e) {
            	System.out.println("bullshit2");
            }
      
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null)
            {
            	facebookGraph();
				Intent intent = new Intent(getApplicationContext(),AddRestaurantActivity.class);
				startActivity(intent);
				finish();
            }

		//callback to handle the results of the login attempts and register it 
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
				{

			@Override
			public void onSuccess(LoginResult result) {

				/*System.out.println("User ID: "
						+ result.getAccessToken().getUserId()
						+ "\n" +
						"Auth Token: "
						+ result.getAccessToken().getToken());*/
				facebookGraph();
				Intent intent = new Intent(getApplicationContext(),AddRestaurantActivity.class);
				startActivity(intent);
				finish();

			}

			@Override
			public void onCancel() {
				System.out.println("ATTEPT CANCLED");
			}

			@Override
			public void onError(FacebookException error) {
				System.out.println("ATTEPT FAILED");

			} 

				});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	private void facebookGraph()
	{
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		System.out.println(accessToken.getToken());
		GraphRequest request = GraphRequest.newMeRequest(
				accessToken,
				new GraphRequest.GraphJSONObjectCallback() {
					

					@Override
					public void onCompleted(JSONObject object,
							GraphResponse response) {
						// TODO Auto-genString id,name;
						try {
							id = object.get("id").toString();
							name = object.get("name").toString();
							System.out.println("Name = " + name);
							System.out.println(("id = " + id));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id,name,link");
		request.setParameters(parameters);
		request.executeAsync();
		parameters.putString("fields", "id,name,link");
	}

}
