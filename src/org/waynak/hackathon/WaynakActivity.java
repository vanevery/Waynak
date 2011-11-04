package org.waynak.hackathon;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class WaynakActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView i = (ImageView) this.findViewById(R.id.mainImageView);
        i.setImageResource(R.drawable.waynak_splash);        
        

        new Handler().postDelayed(new Runnable() {
			
			public void run() {
				Intent mainIntent = new Intent(getApplicationContext(),MapActivity.class);
				WaynakActivity.this.startActivity(mainIntent);
				WaynakActivity.this.finish();
				
			}
		}, SPLASH_DISPLAY_LENGHT);
    }
}