package org.waynak.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EventsActivity extends Activity
{
	private ListView eventsListView;

	private Event[] eventList;

	int clickedEventPosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.events);
		
		
		ImageView iv = (ImageView) this.findViewById(R.id.descImg);
		iv.setImageResource(R.drawable.nyuad);
		
		TextView tv = (TextView) this.findViewById(R.id.descTv);
		tv.setText("NYU Abu Dhabi");
		
		//String [] events = {"Souk " , "Qasr al-Hosn" , "Hatta Heritage Watchover" , "Eastern Fort" , "Jabel Hafit Tombs"};

		eventList = new Event[5];
		eventList[0] = new Event("Souk",R.drawable.fish_souk,"Old Abu Dhabi Souk (1962)");
		eventList[1] = new Event("Qasr al-Hosn",R.drawable.hosn1,"Qasr Al Hosn in the past");
		eventList[2] = new Event("Hatta Heritage Watchover",R.drawable.hosn12,"Here is the description");
		eventList[3] = new Event("Eastern Fort",R.drawable.hosn12,"Here is the description");
		eventList[4] = new Event("Jabel Hafit Tombs",R.drawable.hosn12,"Here is the description");
		/*
		eventList[0] = new Event("Souk","android.resource://org.waynak.hackathon/" + R.drawable.fish_souk,"Here is the description");
		eventList[1] = new Event("Qasr al-Hosn","android.resource://org.waynak.hackathon/" + R.drawable.hosn1,"Here is the description");
		eventList[2] = new Event("Hatta Heritage Watchover","android.resource://org.waynak.hackathon/" + R.drawable.hosn12,"Here is the description");
		eventList[3] = new Event("Eastern Fort","android.resource://org.waynak.hackathon/" + R.drawable.hosn12,"Here is the description");
		eventList[4] = new Event("Jabel Hafit Tombs","android.resource://org.waynak.hackathon/" + R.drawable.hosn12,"Here is the description");
		*/
		eventsListView = (ListView) findViewById(R.id.eventsList);
		eventsListView.setAdapter(new EventsAdapater(this, R.layout.events_row, eventList));
		
		eventsListView.setOnItemClickListener(new OnItemClickListener() {
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			      clickedEventPosition = position;
			      Toast.makeText(getApplicationContext(), eventList[position].eventName, Toast.LENGTH_SHORT).show();
				Intent i = new Intent(EventsActivity.this,ImageAvctivity.class);
				i.putExtra("resource",eventList[clickedEventPosition].imageId);
				i.putExtra("title", eventList[clickedEventPosition].eventName);
				i.putExtra("description", eventList[clickedEventPosition].description);
				startActivity(i);
			      TextView mainText = (TextView) findViewById(R.id.mainText);
			      mainText.setText(eventList[position].description);
			    }
		});

	}
}
