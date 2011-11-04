package org.waynak.hackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CameraActivity extends Activity implements LocationListener {
	 final static int CAMERA_RESULT = 0;

     ImageView imv;
     String imageFilePath;
     Uri imageFileUri;

 @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.cam);
     gps();
     // Save the name and description of an image in a ContentValues map.
     /*
     ContentValues contentValues = new ContentValues(3);
     contentValues.put(Media.DISPLAY_NAME, "This is a test title");
     contentValues.put(Media.DESCRIPTION, "This is a test description");
     contentValues.put(Media.MIME_TYPE, "image/jpeg");
     */
     // Add a new record without the bitmap, but with the values just set.
     // insert() returns the URI of the new record.
     imageFileUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
     Log.v("imageFileUri",imageFileUri.toString());

             Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
     //Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
     i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
             startActivityForResult(i, CAMERA_RESULT);

 }

 protected void onActivityResult(int requestCode, int resultCode, Intent intent)
 {
             super.onActivityResult(requestCode, resultCode, intent);

             if (resultCode == RESULT_OK)
             {
                     // Get a reference to the ImageView
                     imv = (ImageView) findViewById(R.id.img);

                     /*
                     // Get Extras from the intent

                     Bundle extras = intent.getExtras();

                     // Get the returned image from that extra
                     Bitmap bmp = (Bitmap) extras.get("data");

                     imv.setImageBitmap(bmp);
                     */


                     Display currentDisplay = getWindowManager().getDefaultDisplay();
                     int dw = currentDisplay.getWidth();
                     int dh = currentDisplay.getHeight();

                     try
                     {
                             // Load up the image's dimensions not the image itself
                             BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                             bmpFactoryOptions.inJustDecodeBounds = true;
                             Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);

                     int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
                     int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dw);

                     Log.v("HEIGHTRATIO",""+heightRatio);
                     Log.v("WIDTHRATIO",""+widthRatio);

                     // If both of the ratios are greater than 1, one of the sides of the image is greater than the screen
                     if (heightRatio > 1 && widthRatio > 1)
                     {
                             if (heightRatio > widthRatio)
                             {
                                     // Height ratio is larger, scale according to it
                                     bmpFactoryOptions.inSampleSize = heightRatio;
                             }
                             else
                             {
                                     // Width ratio is larger, scale according to it
                                     bmpFactoryOptions.inSampleSize = widthRatio;
                             }
                     }

                     // Decode it for real
                     bmpFactoryOptions.inJustDecodeBounds = false;
                             bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);

                     // Display it
                     imv.setImageBitmap(bmp);
                     
                     imv.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
	                    	finish();							
						} 
                     });

                     // Update the record with Title and Description
                 ContentValues contentValues = new ContentValues(3);
                 contentValues.put(Media.DISPLAY_NAME, "This is a test title");
                 contentValues.put(Media.DESCRIPTION, "This is a test description");
                 getContentResolver().update(imageFileUri,contentValues,null,null);

                     }
                     catch (FileNotFoundException e)
                     {
                             Log.v("ERROR",e.toString());
                     }

                     /*
                     // The columns that we want from the query
             String[] columns = {Media.DATA, Media._ID};
             Cursor cursor = managedQuery(imageFileUri, columns, null, null, null);
             int fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
             if (cursor.moveToFirst())
             {
                     imageFilePath = cursor.getString(fileColumn);

                     Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);

                     int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
                     int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dw);

                     Log.v("HEIGHTRATIO",""+heightRatio);
                     Log.v("WIDTHRATIO",""+widthRatio);

                     // If both of the ratios are greater than 1, one of the sides of the image is greater than the screen
                     if (heightRatio > 1 && widthRatio > 1)
                     {
                             if (heightRatio > widthRatio)
                             {
                                     // Height ratio is larger, scale according to it
                                     bmpFactoryOptions.inSampleSize = heightRatio;
                             }
                             else
                             {
                                     // Width ratio is larger, scale according to it
                                     bmpFactoryOptions.inSampleSize = widthRatio;
                             }
                     }

                     // Decode it for real
                     bmpFactoryOptions.inJustDecodeBounds = false;
                     bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);

                     // Display it
                     imv.setImageBitmap(bmp);
             }
             */
                     //calling the gps 
                    
                     
                     
              /*       
              Intent myIntent = new Intent(getApplicationContext(), EventsActivity.class);
            
              startActivity(myIntent);
               */      
             }
             
             
 }
 LocationManager lm;
 String lat , lot ; 
 public void gps(){
	 
	   lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000l, 5.0f, this);
       lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000l, 5.0f, this);
	 
 }
 public void onPause()
 {
         super.onPause();
         lm.removeUpdates(this);
 }
public void onLocationChanged(Location location) {
	 Log.v("LOCATION", "Provider: " + location.getProvider());
    // tv.setText(location.getLatitude() + " " + location.getLongitude());
	 lat = location.getLatitude()+"" ;
	 lot=location.getLongitude()+"";
	 
     Log.v("LOCATION", "onLocationChanged: lat=" + location.getLatitude() + ", lon=" + location.getLongitude());
}

public void onProviderDisabled(String provider) {
	 Log.v("LOCATION", "onProviderDisabled: " + provider);
	
}

public void onProviderEnabled(String provider) {
	Log.v("LOCATION", "onProviderEnabled: " + provider);
	
}

public void onStatusChanged(String provider, int status, Bundle extras) {
	 Log.v("LOCATION", "onStatusChanged: " + provider + " status:" + status);
     if (status == LocationProvider.AVAILABLE) {
             Log.v("LOCATION","Provider Available");
     } else if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
             Log.v("LOCATION","Provider Temporarily Unavailable");
     } else if (status == LocationProvider.OUT_OF_SERVICE) {
             Log.v("LOCATION","Provider Out of Service");
     }
	
}

}
