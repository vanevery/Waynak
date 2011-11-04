package org.waynak.hackathon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAvctivity extends Activity implements OnTouchListener {
	
	private static final String TAG = "IMAGEVIEWERMATRIX";

    // Image Matrix
    Matrix matrix = new Matrix();

    // Saved Matrix
    Matrix savedMatrix = new Matrix();

    // Initial Matrix
    Matrix baseMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    static final float MAX_SCALE = 10f;

    // For Zooming
    float startFingerSpacing = 0f;
    float endFingerSpacing = 0f;
    PointF startFingerSpacingMidPoint = new PointF();

    // For Dragging
    PointF startPoint = new PointF();

    ImageButton zoomIn, zoomOut;
    ImageView view;

    Bitmap realBmp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	       requestWindowFeature(Window.FEATURE_NO_TITLE);
	               setContentView(R.layout.image);
	               view = (ImageView) findViewById(R.id.imageView);

	               //zoomIn = (ImageButton) this.findViewById(R.id.ZoomIn);
	               //zoomOut = (ImageButton) this.findViewById(R.id.ZoomOut);

	              // zoomIn.setOnClickListener(this);
	               //zoomOut.setOnClickListener(this);

	              // Uri imageUri = getIntent().getData();
	               Intent i = this.getIntent();

	               /*
					i.putExtra("title", eventList[clickedEventPosition].eventName);
					i.putExtra("description", eventList[clickedEventPosition].description);	               
	               */

	               TextView tvTitle = (TextView) this.findViewById(R.id.eventTitle);
	               tvTitle.setText(i.getExtras().getString("title"));
	               TextView tvDesc = (TextView) this.findViewById(R.id.eventDesc);
	               tvDesc.setText(i.getExtras().getString("description"));
	               
	             //  try {
	                       //URL imageUrl = new URL(imageUri.toString());

	                       //realBmp = BitmapFactory.decodeStream(imageUrl.openStream());
	            	   		realBmp = BitmapFactory.decodeResource(this.getResources(), i.getExtras().getInt("resource"));

	                       Display display = getWindowManager().getDefaultDisplay();
	                       int width = display.getWidth();
	                       int height = display.getHeight();

	                       Log.v(TAG, "Display Width: " + display.getWidth());
	                       Log.v(TAG, "Display Height: " + display.getHeight());

	                       Log.v(TAG, "BMP Width: " + realBmp.getWidth());
	                       Log.v(TAG, "BMP Height: " + realBmp.getHeight());

	                       if (realBmp.getWidth() > width || realBmp.getHeight() > height) {

	                               float heightRatio = (float) height / (float) realBmp.getHeight();
	                               float widthRatio = (float) width / (float) realBmp.getWidth();

	                               Log.v(TAG, "heightRatio:" + heightRatio);
	                               Log.v(TAG, "widthRatio: " + widthRatio);

	                               float scale = widthRatio;
	                               if (heightRatio < widthRatio) {
	                                       scale = heightRatio;
	                               }

	                               matrix.setScale(scale, scale);
	                               Log.v(TAG, "Scale: " + scale);
	                       } else {
	                               Log.v(TAG, "NOTNOTNOT");
	                               matrix.setTranslate(1f, 1f);
	                       }

	                       view.setImageBitmap(realBmp);
	                       view.setImageMatrix(matrix);
	                       baseMatrix.set(matrix);
	                       view.setOnTouchListener(this);

	                       //view.setBackgroundColor(R.color.orange);
	                       Log.v(TAG,"View Left, Right: " + view.getLeft() + " " + view.getRight());
	               
	                       /*

	               } catch (MalformedURLException e) {
	                       e.printStackTrace();
	               } catch (IOException e) {
	                       e.printStackTrace();
	                
	               }
					*/
		
		
	}


	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                        savedMatrix.set(matrix);

                        // Save the Start point.  We have a single finger so it is drag
                        startPoint.set(event.getX(), event.getY());
                        mode = DRAG;
                        Log.d(TAG, "mode=DRAG");

                        break;

                case MotionEvent.ACTION_POINTER_DOWN:

                        // Get the spacing of the fingers, 2 fingers
                        float sx = event.getX(0) - event.getX(1);
                        float sy = event.getY(0) - event.getY(1);
                        startFingerSpacing = (float) Math.sqrt(sx * sx + sy * sy);

                        Log.d(TAG, "Start Finger Spacing=" + startFingerSpacing);

                        if (startFingerSpacing > 10f) {

                                float xsum = event.getX(0) + event.getX(1);
                                float ysum = event.getY(0) + event.getY(1);
                                startFingerSpacingMidPoint.set(xsum / 2, ysum / 2);

                                mode = ZOOM;
                                Log.d(TAG, "mode=ZOOM");
                        }

                        break;

                case MotionEvent.ACTION_UP:
                        // Nothing

                case MotionEvent.ACTION_POINTER_UP:

                        mode = NONE;
                        Log.d(TAG, "mode=NONE");
                        break;

                case MotionEvent.ACTION_MOVE:

                        if (mode == DRAG) {

                                matrix.set(savedMatrix);
                                matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                                view.setImageMatrix(matrix);
                                putOnScreen();

                        } else if (mode == ZOOM) {

                                // Get the spacing of the fingers, 2 fingers
                                float ex = event.getX(0) - event.getX(1);
                                float ey = event.getY(0) - event.getY(1);
                                endFingerSpacing = (float) Math.sqrt(ex * ex + ey * ey);

                                Log.d(TAG, "End Finger Spacing=" + endFingerSpacing);

                                if (endFingerSpacing > 10f) {
                                        matrix.set(savedMatrix);

                                        // Ratio of spacing..  If it was 5 and goes to 10 the image is 2x as big
                                        float scale = endFingerSpacing / startFingerSpacing;
                                        // Scale from the midpoint
                                        matrix.postScale(scale, scale, startFingerSpacingMidPoint.x, startFingerSpacingMidPoint.y);

                                        float[] matrixValues = new float[9];
                                        matrix.getValues(matrixValues);
                                        Log.v(TAG, "Total Scale: " + matrixValues[0]);
                                        Log.v(TAG, "" + matrixValues[0] + " " + matrixValues[1]
                                                        + " " + matrixValues[2] + " " + matrixValues[3]
                                                        + " " + matrixValues[4] + " " + matrixValues[5]
                                                        + " " + matrixValues[6] + " " + matrixValues[7]
                                                        + " " + matrixValues[8]);
                                        if (matrixValues[0] > MAX_SCALE) {
                                                matrix.set(savedMatrix);
                                        }
                                        view.setImageMatrix(matrix);
                                        putOnScreen();
                                }
                        }
                        break;
        }

        return true; // indicate event was handled

	}//onTouch
	
	public void putOnScreen() {

        Log.v(TAG,"View Left, Right: " + view.getLeft() + " " + view.getRight());
        Log.v(TAG,"View Width, Height: " + view.getWidth() + " " + view.getHeight());

        // Get Rectangle of Tranformed Image
        //Matrix currentDisplayMatrix = new Matrix();
        //currentDisplayMatrix.set(baseMatrix);
        //currentDisplayMatrix.postConcat(matrix);

        RectF theRect = new RectF(0,0,realBmp.getWidth(), realBmp.getHeight());
        //currentDisplayMatrix.mapRect(theRect);
        matrix.mapRect(theRect);

        Log.v(TAG,"Rect Width, Height: " + theRect.width() + " " + theRect.height());
        Log.v(TAG,"Rect Top, Left, Bottom, Right: " + theRect.top + " " + theRect.left + " " + theRect.bottom + " " + theRect.right);

        float deltaX = 0, deltaY = 0;
        if (theRect.width() < view.getWidth()) {
                //deltaX = (view.getWidth() - theRect.width())/2 - theRect.left;
                deltaX = 0 - theRect.left;
                Log.v(TAG,"CENTERING HORIZONTAL");
        } else if (theRect.left > 0) {
                deltaX = -theRect.left;
                Log.v(TAG,"Pinning Left");
        } else if (theRect.right < view.getWidth()) {
                deltaX = view.getWidth() - theRect.right;
                Log.v(TAG,"Pinning Right");
        }

        if (theRect.height() < view.getHeight()) {
                //deltaY = (view.getHeight() - theRect.height())/2 - theRect.top;
                deltaY = 0 - theRect.top;
                Log.v(TAG,"CENTERING VERTICAL");
        } else if (theRect.top > 0) {
                deltaY = -theRect.top;
                Log.v(TAG,"Pinning Top");
        } else if (theRect.bottom < view.getHeight()) {
                deltaY = view.getHeight() - theRect.bottom;
                Log.v(TAG,"Pinning Bottom");
        }

        Log.v(TAG,"Deltas:" + deltaX + " " + deltaY);

        matrix.postTranslate(deltaX,deltaY);
        view.setImageMatrix(matrix);
}


}
