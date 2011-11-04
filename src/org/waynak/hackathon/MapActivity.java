package org.waynak.hackathon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MapActivity extends com.google.android.maps.MapActivity implements LocationListener {
	
	public static final String LOGTAG = "MAP";
	
	LocationManager lm; 
	MapView mv;
	GeoPoint p;

     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mv = (MapView) this.findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		
		// Create the overlays
		List<Overlay> mapOverlays = mv.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.waynak);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		MyLocationOverlay itemizedoverlay = new MyLocationOverlay(drawable, this);
		
		// Using the same point for now
		GeoPoint point = new GeoPoint((int) (24.4854827 * 1000000), (int) (54.351599 * 1000000));

		OverlayItem overlayitem = new OverlayItem(point, "NYUAD", "New York University Abu Dhabi");
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);		

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000l, 5.0f, this);	}
	 /*
     * Standard method for menu items.  Uses res/menu/image_editor_menu.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    	MenuInflater inflater = getMenuInflater();
    
    	inflater.inflate(R.menu.menu, menu);

    	return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent myIntent=null;
		// Handle item selection
	    switch (item.getItemId()) {
	    case R.id.cameraItem:
	        myIntent=new Intent(getApplicationContext(),CameraActivity.class);
	    	startActivity(myIntent);
	    	return true;
	    /*case R.id.recentItem:
	    	myIntent=new Intent(getApplicationContext(),RecentActivity.class);
	    	startActivity(myIntent);
	        return true;*/
	    case R.id.eventItem :
	    	myIntent= new Intent(getApplicationContext(),EventsActivity.class);
	    	startActivity(myIntent);
	    	return true ;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLocationChanged(Location location) {
	Log.v("MAPTEST",location.getLatitude() + " " + location.getLongitude());
		
		MapController mc = mv.getController();
		
		p = new GeoPoint((int) (location.getLatitude() * 1000000), (int) (location.getLongitude() * 1000000));
		mc.animateTo(p);
		//http://code.google.com/android/add-ons/google-apis/reference/com/google/android/maps/MapController.html#setZoom(int)
		mc.setZoom(14);
		
	
	}

        /*
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
        List<Overlay> list = mv.getOverlays();
        list.add(myLocationOverlay);
        */

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
    
	

    public void onPause() {
		super.onPause(); 
		lm.removeUpdates(this);
	}

    /*
	class MyLocationOverlay extends com.google.android.maps.Overlay {
	     public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
             super.draw(canvas, mapView, shadow);
             Paint paint = new Paint();
             paint.setColor(Color.BLUE);
             //paint.setColor(Color.argb(0, 255, 0, 0));
             canvas.drawRect(0, 0, mv.getWidth()/2, mv.getHeight()/2, paint);
     // Converts lat/lng-Point to OUR coordinates on the screen.
     Point myScreenCoords = new Point();
     mapView.getProjection().toPixels(p, myScreenCoords);
     paint.setStrokeWidth(1);
     paint.setARGB(255, 255, 255, 255);
     paint.setStyle(Paint.Style.STROKE);

     canvas.drawText("Here I am...", myScreenCoords.x, myScreenCoords.y, paint);
     return true;
     }
	}	
*/
    
    class MyLocationOverlay extends ItemizedOverlay<OverlayItem> {

    	public static final String LOGTAG = "LOCATIONOVERLAY";
    	
    	ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    	Context mContext;
    	
    	public MyLocationOverlay(Drawable defaultMarker) {
    		  super(boundCenterBottom(defaultMarker));
    	}
    	
    	public MyLocationOverlay(Drawable defaultMarker, Context context) {
    		  super(defaultMarker);
    		  mContext = context;
    	}

    	@Override
    	protected OverlayItem createItem(int i) {
    		  return mOverlays.get(i);
    	}

    	@Override
    	public int size() {
    		  return mOverlays.size();
    	}
    	
    	public void addOverlay(OverlayItem overlay) {
    	    mOverlays.add(overlay);
    	    populate();
    	}
    	
    	@Override
    	public boolean onTap(int index) {
    		Log.v(LOGTAG,"onTap");
    		Toast.makeText(mContext,
    				mOverlays.get(index).getSnippet(),
                    Toast.LENGTH_SHORT).show();
    		
    		Intent myIntent= new Intent(MapActivity.this,EventsActivity.class);
        	startActivity(myIntent);
    		
    	  return true;
    	}
    }
}
