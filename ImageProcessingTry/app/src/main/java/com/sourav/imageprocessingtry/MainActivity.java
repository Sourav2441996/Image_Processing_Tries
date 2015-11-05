package com.sourav.imageprocessingtry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=(ImageView)findViewById(R.id.image);
        id=R.drawable.jolie;
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float halfX=view.getWidth()/2;
                float x=(int)motionEvent.getX();
                Bitmap b=((BitmapDrawable)image.getDrawable()).getBitmap();
                if(b!=null)
                {
                    float factor=x/halfX;
                    image.setImageBitmap(StylesLogic.adjustBrightness(b,factor));
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setOriginal(View view) {
        Log.v("imaging", image.getMeasuredWidth()+" by "+ image.getMeasuredHeight());
        float up=System.currentTimeMillis();

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), id);

        Toast.makeText(this, "The size of the image is "+ (bitmap.getRowBytes()*bitmap.getHeight()/1024)+" and time" +
                "it took is "+ (System.currentTimeMillis()-up),Toast.LENGTH_SHORT).show();
        image.setImageBitmap(bitmap);

    }

    public void setFit(View view) {
        Log.v("imaging", image.getMeasuredWidth()+" by "+ image.getMeasuredHeight());
        float up=System.currentTimeMillis();

        Bitmap bitmap=(ScalingLogic.decodeAndScale(getResources(),id, image.getMeasuredWidth(), image.getMeasuredHeight(),ScalingLogic.FIT));

        Toast.makeText(this, "The size of the image is "+ (bitmap.getRowBytes()*bitmap.getHeight()/1024)+" and time" +
                "it took is "+ (System.currentTimeMillis()-up),Toast.LENGTH_SHORT).show();
        image.setImageBitmap(bitmap);
    }

    public void setCrop(View view) {
        Log.v("imaging", image.getMeasuredWidth()+" by "+ image.getMeasuredHeight());
        float up=System.currentTimeMillis();

        Bitmap bitmap=(ScalingLogic.decodeAndScale(getResources(),id, image.getMeasuredWidth(), image.getMeasuredHeight(),ScalingLogic.CROP));

        Toast.makeText(this, "The size of the image is "+ (bitmap.getRowBytes()*bitmap.getHeight()/1024)+" and time" +
                "it took is "+ (System.currentTimeMillis()-up),Toast.LENGTH_SHORT).show();
        image.setImageBitmap(bitmap);
    }

    public void createGrayscale(View view) {
        Bitmap original=(ScalingLogic.decodeAndScale(getResources(),id, image.getMeasuredWidth(), image.getMeasuredHeight(),ScalingLogic.FIT));
        image.setImageBitmap(StylesLogic.lumGrayscale(original));
    }

    public void createInvert(View view) {
        Bitmap original=(ScalingLogic.decodeAndScale(getResources(),id, image.getMeasuredWidth(), image.getMeasuredHeight(),ScalingLogic.FIT));
        image.setImageBitmap(StylesLogic.invert(original));
    }

    public void createGrayscaleInvert(View view) {
        Bitmap original=(ScalingLogic.decodeAndScale(getResources(),id, image.getMeasuredWidth(), image.getMeasuredHeight(),ScalingLogic.FIT));
        Bitmap grayscale=StylesLogic.lumGrayscale(original);
        image.setImageBitmap(StylesLogic.invert(grayscale));
    }

    public void createBlur(View view) {
        Bitmap original=((BitmapDrawable)image.getDrawable()).getBitmap();
        image.setImageBitmap(StylesLogic.gaussianBlur(original,3));
    }

    public void createVinette(View view) {
        Bitmap original=((BitmapDrawable)image.getDrawable()).getBitmap();
        image.setImageBitmap(StylesLogic.gamma(StylesLogic.vignette(original),0.55));
    }

    public void seeOthres(View view) {
        Intent i=new Intent(this,OthersActivity.class);
        startActivity(i);
    }
}
