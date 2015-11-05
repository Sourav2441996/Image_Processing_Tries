package com.sourav.imageprocessingtry;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ASUS on 12/09/2015.
 */
public abstract class ScalingLogic {
    public static final int CROP=0;
    public static final int FIT=1;

    public static Rect calculateSrcRect(int srcwidth, int srcheight, int dstwidth, int dstheight, int scalingLogic)
    {


        if(scalingLogic==ScalingLogic.CROP)
        {
            final float srcAspect=(float)srcwidth/srcheight;
            final float dstAspect =(float)dstwidth/dstheight;

            if(srcAspect>dstAspect) {
                final int srcRectWidth = (int) (srcheight * dstAspect);
                final int srcLeft = (int) (srcwidth - srcRectWidth) / 2;
                return new Rect(srcLeft, 0, srcLeft + srcRectWidth, srcheight);

            }
            else
            {
                final int srcRectHeight=(int)(srcwidth/dstAspect);
                final int srcRectTop=(int)(srcheight-srcRectHeight)/2;
                return new Rect(0,srcRectTop,srcwidth, srcRectTop+srcheight);
            }
        }
        else
            return new Rect(0,0, srcwidth, srcheight);
    }


    public static Rect calculateDstRect(int srcwidth, int srcheight, int dstwidth, int dstheight, int scalingLogic)
    {
        if(scalingLogic==ScalingLogic.FIT)
        {
            final float srcAspect=(float)srcwidth/(float)srcheight;
            final float dstAspect=(float )dstwidth/(float)dstheight;

            if(srcAspect>dstAspect)
            {
                return new Rect(0,0,dstwidth,(int)(dstwidth/srcAspect));

            }
            else
                return new Rect(0,0, (int)(srcAspect*dstheight), dstheight);
        }
        else
            return new Rect(0,0, dstwidth, dstheight);
    }

    public static Bitmap scaleBitmap(Bitmap unscaledBitmap, int dstwidth, int dstheight, int scalingLogic)
    {
        Rect srcRect=calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstwidth, dstheight, scalingLogic);
        Rect dstRect=calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstwidth, dstheight, scalingLogic);
        Bitmap scaledBitmap=Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;

    }


    public static Bitmap decodeBitmap(Resources rs, int rsID, int dstwidth, int dstheight, int scalingLogic)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(rs, rsID, options);
        options.inSampleSize=calculateInSampleSize(options.outWidth, options.outHeight, dstwidth,dstheight, scalingLogic);
        options.inJustDecodeBounds=false;
        Bitmap decoded=BitmapFactory.decodeResource(rs,rsID, options);
        return decoded;

    }

    public static int calculateInSampleSize(int srcwidth, int srcheight, int dstwidth, int dstheight,int scalingLogic)
    {
        if(scalingLogic==ScalingLogic.FIT)
        {
            final int srcAspect=(int)srcwidth/srcheight;
            final int dstAspect=(int)dstwidth/dstheight;
            if(srcAspect>dstAspect)
                return srcwidth/dstwidth;
            else
                return srcheight/dstheight;
        }
        else
        {
            final int srcAspect=srcwidth/srcheight;
            final int dstAspect=dstwidth/dstheight;
            if(srcAspect>dstAspect)
                return srcheight/dstheight;
            else
                return srcwidth/dstwidth;
        }
    }


    public static Bitmap decodeAndScale(Resources rs, int rsId, int dstwidth, int dstheight, int scalingLogic)
    {
        Bitmap unscaled=decodeBitmap(rs, rsId,dstwidth,dstheight,scalingLogic);
        Bitmap scaled=scaleBitmap(unscaled, dstwidth, dstheight, scalingLogic);
        return scaled;
    }
}
