package org.waynak.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecentActivity extends Activity implements OnItemClickListener {

	private ListView recentPlacesList ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recent_places);
		
		recentPlacesList=(ListView)findViewById(R.id.recentPlacesList);
		
		String [] places = {"dubai " , "abu dhabi" , "amman "};
		
		// this.setListAdapter(new MyAdapter(this, title, desc, pubDate));
		recentPlacesList.setAdapter(new RecentAdapter(this, places, places));
		recentPlacesList.setOnItemClickListener(this);
		
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
