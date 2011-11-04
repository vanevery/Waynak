package org.waynak.hackathon;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] eventName;
	private final String [] imageList ; 
	
	
	public RecentAdapter(Activity context, String [] event ,String [] i) {
		super(context, R.layout.recent_row, event);
		this.context=context;
		this.eventName=event;
		this.imageList=i;
		
	}
	
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public ImageView imageView ; 
		public TextView eventView;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout
		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater =  context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.recent_row, null, true);
			holder = new ViewHolder();
			holder.eventView = (TextView) rowView.findViewById(R.id.recentTv);
			holder.imageView=(ImageView)rowView.findViewById(R.id.recentImg);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		String t = eventName[position];
		String d= imageList[position];
		
		holder.eventView.setText(t);
		//URL url = new URL(imageList[position]);
		
		
		InputStream is=null;
		
		String sss = "http://www.cars-directory.net/pics/opel/omega/1988/opel_omega_1017239.jpg";
		
		Bitmap x = imageFromUrl(sss);
	//Drawable ddd = x.get
        holder.imageView.setImageBitmap(x);

		//holder.descView.setText(d);
		
		
		
		
		return rowView;
	}
	
	
	
	
	public Bitmap imageFromUrl(String url) {
		Bitmap bitmapImage;
		URL imageUrl = null;
		try {
		imageUrl = new URL(url);
		} catch (MalformedURLException e) {
		e.printStackTrace();
		}
		try {
		HttpURLConnection httpConnection =
		(HttpURLConnection) imageUrl.openConnection();
		httpConnection.setDoInput(true);
		httpConnection.connect();
		InputStream is = httpConnection.getInputStream();
		bitmapImage = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		e.printStackTrace();
		bitmapImage = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
		}
		return bitmapImage;
		}
		}
		
	

