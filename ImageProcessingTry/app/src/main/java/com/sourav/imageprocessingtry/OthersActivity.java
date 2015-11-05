package com.sourav.imageprocessingtry;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class OthersActivity extends AppCompatActivity {

    ImageView image2;
    int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        image2=(ImageView)findViewById(R.id.image2);
        final ViewTreeObserver observer=image2.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                image2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width=image2.getMeasuredWidth();
                height=image2.getMeasuredHeight();
                image2.setImageBitmap(ScalingLogic.decodeAndScale(getResources(), R.drawable.selfie1, width, height, ScalingLogic.CROP));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others, menu);
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

    public void setTint1(View view) {
        Bitmap a=ScalingLogic.decodeAndScale(getResources(), R.drawable.selfie1, width, height, ScalingLogic.CROP);
        a=StylesLogic.tint1(a);
        image2.setImageBitmap(a);

    }

    public void setTint2(View view)
    {
        Bitmap a=ScalingLogic.decodeAndScale(getResources(), R.drawable.selfie1, width, height, ScalingLogic.CROP);
       // a=StylesLogic.tint(a, 1, .34, .13);
        /*a=StylesLogic.adjustBrightness(a, 1.5f);
        a=StylesLogic.tint(a, 0.8, 0.8, .8);
       // a=StylesLogic.vignette(a);
        a=StylesLogic.contrast(a, 50);
        a =StylesLogic.gamma(a, 5);
        a=StylesLogic.contrast(a, 50);
        //a = StylesLogic.adjustBrightness(a,1.3f);*/
        a=StylesLogic.tint2(a);
       // a=StylesLogic.vignette(a);

        image2.setImageBitmap(a);
    }

    public void setTint3(View view) {
        Bitmap a=ScalingLogic.decodeAndScale(getResources(), R.drawable.selfie1, width, height, ScalingLogic.CROP);
        a=StylesLogic.tint3(a);
        image2.setImageBitmap(a);
    }

    public void setTint4(View view) {
        Bitmap a=ScalingLogic.decodeAndScale(getResources(), R.drawable.selfie1, width, height, ScalingLogic.CROP);
        a=StylesLogic.trialMethod(a);
        image2.setImageBitmap(a);
    }

    public void setSwe(View view) {
    }
}
