package com.MOS.fastfood;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.FacebookSdk;

public class PhotoShare extends FragmentActivity{


	private CallbackManager callbackManager;
	private LoginManager loginManager;


	private static final int CAMERA_REQUEST = 1888; 


	static String str_Camera_Photo_ImagePath = "";
	private static File f;
	private static int Take_Photo = 2;
	private static String str_randomnumber = "";
	static String str_Camera_Photo_ImageName = "";
	public static String str_SaveFolderName;
	private static File wallpaperDirectory;
	Bitmap bitmap;
	int storeposition = 0;
	public static GridView gridview;
	public static ImageView imageView;
	Bitmap faceView;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		List<String> permissionNeeds = Arrays.asList("publish_actions");

		setContentView(R.layout.activity_photo_share);
		this.imageView = (ImageView)this.findViewById(R.id.imageView1);
		Button photoButton = (Button) this.findViewById(R.id.button1);

		LoginManager manager;
		manager = LoginManager.getInstance();
		manager.logInWithPublishPermissions(this, permissionNeeds);

		Dialog dialog = new Dialog(PhotoShare.this);
		dialog.setContentView(R.layout.test);
		dialog.setTitle("Upload A Photo");
		int title_color;
		title_color = getResources().getColor(R.color.title_color);
		dialog.getWindow().setBackgroundDrawableResource(R.color.title_color);
		dialog.show();
		manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
				{
			@Override
			public void onSuccess(LoginResult loginResult)
			{
				
			}

			@Override
			public void onCancel()
			{
				System.out.println("onCancel");
			}

			@Override
			public void onError(FacebookException exception)
			{
				System.out.println("onError");
			}
				});


		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{

				str_SaveFolderName = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/FastFood";
				str_randomnumber = String.valueOf(nextSessionId());
				wallpaperDirectory = new File(str_SaveFolderName);
				if (!wallpaperDirectory.exists())
					wallpaperDirectory.mkdirs();
				str_Camera_Photo_ImageName = str_randomnumber + ".jpg";

				str_Camera_Photo_ImagePath = str_SaveFolderName + "/" + str_randomnumber + ".jpg";

				System.err.println("str_Camera_Photo_ImagePath  " + str_Camera_Photo_ImagePath);

				f = new File(str_Camera_Photo_ImagePath);
				startActivityForResult(new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
								MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)),
								Take_Photo);
				System.err.println("f  " + f);
			}
		});
	}

	public void sharePhotoToFacebook()
	{
		//Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		Bitmap image = faceView;
		SharePhoto photo = new SharePhoto.Builder().setBitmap(image).setCaption("Semi Final").build();
		SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
		ShareApi.share(content, null);
	}
	//Clickable Button
	public void gotoShare(View v)
	{
		sharePhotoToFacebook();

	}

	// used to create randon numbers
	public String nextSessionId() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if (requestCode == Take_Photo) {
			String filePath = null;

			filePath = str_Camera_Photo_ImagePath;
			if (filePath != null) {
				faceView = ( new_decode(new File(filePath))); 
				imageView.setImageBitmap(faceView);
			} else {
				bitmap = null;
			}
		}
	} 


	public static Bitmap new_decode(File f) {

		// decode image size

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		o.inDither = false; // Disable Dithering mode

		o.inPurgeable = true; // Tell to gc that whether it needs free memory,

		o.inInputShareable = true; // Which kind of reference will be used to
		try {
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Find the correct scale value. It should be the power of 2.
		final int REQUIRED_SIZE = 300;
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 1.5 < REQUIRED_SIZE && height_tmp / 1.5 < REQUIRED_SIZE)
				break;
			width_tmp /= 1.5;
			height_tmp /= 1.5;
			scale *= 1.5;
		}

		//BitmapFactory.Options o2 = new BitmapFactory.Options();
		// o2.inSampleSize=scale;
		o.inDither = false; // Disable Dithering mode

		o.inPurgeable = true; // Tell to gc that whether it needs free memory,
		// the Bitmap can be cleared

		o.inInputShareable = true; // Which kind of reference will be used to
		try {
			Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(f), null, null);
			System.out.println(" IW " + width_tmp);
			System.out.println("IHH " + height_tmp);           
			int iW = width_tmp;
			int iH = height_tmp;

			return Bitmap.createScaledBitmap(bitmap, iW, iH, true);

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			System.gc();
			return null;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

}

