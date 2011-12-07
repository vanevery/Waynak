package org.waynak.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class WaynakActivity extends Activity implements OnClickListener {
	private final int SPLASH_DISPLAY_LENGTH = 10000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageView i = (ImageView) this.findViewById(R.id.mainImageView);
        i.setImageResource(R.drawable.waynak_splash);
        i.setClickable(true);
        i.setOnClickListener(this);
        
        new Handler().postDelayed(new Runnable() {
			
			public void run() {
				continueOn();
			}
		}, SPLASH_DISPLAY_LENGTH);
    }
    
    private void continueOn() {
		Intent mainIntent = new Intent(this,WaynakMapActivity.class);
		WaynakActivity.this.startActivity(mainIntent);
		WaynakActivity.this.finish();    	
    }

	public void onClick(View v) {
		continueOn();
	}
}