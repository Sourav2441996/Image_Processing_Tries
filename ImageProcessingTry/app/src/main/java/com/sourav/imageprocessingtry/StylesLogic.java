package com.sourav.imageprocessingtry;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

/**
 * Created by ASUS on 15/09/2015.
 */
public abstract class StylesLogic {
    public static Bitmap avgGreyscale(Bitmap original)
    {
        Bitmap grayscale=Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        int[] averages=new int[766];
        for(int i=0;i<averages.length;i++)
            averages[i]=i/3;


       for(int x=0;x<original.getWidth();x++)
       {
           for(int y=0;y<original.getHeight();y++)
           {

               int pixel=original.getPixel(x, y);
               int alpha=getAlpha(pixel);
               int red=getRed(pixel);
               int green=getGreen(pixel);
               int blue=getBlue(pixel);
               int newPixel=averages[red+green+blue];
               Color c=new Color();
               grayscale.setPixel(x, y,c.argb(alpha, newPixel,newPixel, newPixel));
           }
       }
        return grayscale;
    }

    public static Bitmap lumGrayscale(Bitmap original)
    {
        Bitmap grayscale=Bitmap.createBitmap(original.getWidth(), original.getHeight(),Bitmap.Config.ARGB_8888);
        for(int x=0;x<original.getWidth();x++)
        {
            for(int y=0;y<original.getHeight();y++)
            {
                int pixel=original.getPixel(x, y);
                int red=getRed(pixel);
                int green=getGreen(pixel);
                int blue=getBlue(pixel);
                int alpha=getAlpha(pixel);
                int newpixel=(int)(0.299*red+0.587*green+0.114*blue);
                Color c=new Color();
                grayscale.setPixel(x,y,c.argb(alpha,newpixel, newpixel, newpixel));
            }
        }
        return grayscale;

    }

    public static Bitmap invert(Bitmap original)
    {
        Bitmap invert=Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        for(int x=0;x<original.getWidth();x++)
        {
            for(int y=0;y<original.getHeight();y++)
            {
                int pixel=original.getPixel(x, y);
                int red=getRed(pixel);
                int green=getGreen(pixel);
                int blue=getBlue(pixel);
                int alpha=getAlpha(pixel);
                int invertred=red*-1+255;
                int invertgreen=green*-1+255;
                int invertblue=blue*-1+255;
                Color c=new Color();
                invert.setPixel(x,y,c.argb(alpha, invertred, invertgreen, invertblue));

            }
        }
        return invert;
    }


    public static Bitmap tint1(Bitmap src)
    {
        Bitmap tinted=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        Rect r1=new Rect(0,0,src.getWidth()/2,src.getHeight()/2);
        Rect r2=new Rect(src.getWidth()/2,src.getHeight()/2,src.getWidth(),src.getHeight());
        Paint p1=new Paint();
        Paint p2=new Paint();
        Color c=new Color();
        p1.setColor(c.argb(100,129,216,208));
        p2.setColor(c.argb(100,255,116,140));
        Canvas canvas=new Canvas(tinted);
        Paint p3=new Paint();
        p3.setAlpha(255);
        canvas.drawBitmap(src, 0, 0, p3);
        canvas.drawRect(r1, p1);
        canvas.drawRect(r2,p2);
        return tinted;

    }
    public static Bitmap tint2(Bitmap src)
    {
       /* Bitmap tinted=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        Rect r=new Rect(0,0,src.getWidth(),src.getHeight());
        Paint p=new Paint();
        Color c=new Color();
        p.setColor(c.argb(100, 255, 195, 160));
        Canvas canvas=new Canvas(tinted);
        Paint p2=new Paint();
        p2.setAlpha(255);
        canvas.drawBitmap(src, 0, 0, p2);
        canvas.drawRect(r, p);
        return tinted;*/
        int w=src.getWidth();
        int h=src.getHeight();
        int [] pixels=new int[w*h];
        src.getPixels(pixels, 0, w, 0, 0, w, h);

        double rf,gf,bf;
        int r,g,b,p;
        rf=0.3164;
        gf=0.3418;
        bf=0.3418;
        for(int i=0;i<pixels.length;i++)
        {
            p=pixels[i];
            r=getRed(p);
            g=getGreen(p);
            b=getBlue(p);
            r=(int)((double)r*rf);
            g=(int)((double)g*gf);
            b=(int)((double)b*bf);
            pixels[i]=255<<24| (r)<<16 | g<<8 | b<<0;
        }
        src.setPixels(pixels, 0, w, 0, 0, w, h);
        src=StylesLogic.adjustBrightness(src,2.8f);
        return  src;

    }

    public static Bitmap tint3(Bitmap src)
    {
        Bitmap tinted=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        Rect r=new Rect(0,0,src.getWidth(),src.getHeight()/2);
        Paint p=new Paint();
        Color c=new Color();

        p.setShader(new LinearGradient(src.getWidth()/2,0,src.getWidth()/2,src.getHeight()/2,c.argb(90,198,226,255),c.argb(0,255,255,255), Shader.TileMode.MIRROR));
        Canvas canvas=new Canvas(tinted);

        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawRect(r, p);
        Paint p2=new Paint();
        Rect r2=new Rect(0,src.getHeight()/2,src.getWidth(),src.getHeight());
        p2.setShader(new LinearGradient(src.getWidth() / 2, src.getHeight(), src.getWidth() / 2, src.getHeight() / 2, c.argb(80, 255, 255, 50), c.argb(0, 255, 255, 255), Shader.TileMode.CLAMP));
        canvas.drawRect(r2, p2);
        return tinted;

    }

    public static Bitmap tint4(Bitmap src) {
        Bitmap tinted=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Rect r=new Rect(0,0,src.getWidth(),src.getHeight());
        Paint p=new Paint();
        Color c=new Color();
        p.setShader(new LinearGradient(src.getWidth(), 0, src.getWidth() / 2, src.getHeight() / 2, c.argb(90, 178, 102, 178), c.argb(0, 255, 255, 255), Shader.TileMode.CLAMP));
        Canvas canvas=new Canvas(tinted);
        canvas.drawBitmap(src, 0, 0, null);
        canvas.drawRect(r, p);
        p.setShader(new LinearGradient(0, src.getHeight(), src.getWidth() / 2, src.getHeight() / 2, c.argb(90, 178, 102, 178), c.argb(0, 255, 255, 255), Shader.TileMode.CLAMP));
        canvas.drawRect(r,p);
        return tinted;

    }

    public static Bitmap trialMethod(Bitmap src)
    {
        int w=src.getWidth();
        int h=src.getHeight();
        int [] pixels=new int[w*h];
        src.getPixels(pixels, 0, w, 0, 0, w, h);

        double rf,gf,bf;
        int r,g,b,p;
        rf=0.3522;
        gf=0.3522;
        bf=0.2956;
        for(int i=0;i<pixels.length;i++)
        {
            p=pixels[i];
            r=getRed(p);
            g=getGreen(p);
            b=getBlue(p);
            r=(int)((double)r*rf);
            g=(int)((double)g*gf);
            b=(int)((double)b*bf);
            pixels[i]=255<<24| (r)<<16 | g<<8 | b<<0;
        }
        src.setPixels(pixels, 0, w, 0, 0, w, h);
        src=StylesLogic.adjustBrightness(src,2.8f);
        return  src;
       /* Bitmap tinted=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Rect r=new Rect(0,0,src.getWidth(),src.getHeight());
        Paint p=new Paint();
        Canvas canvas=new Canvas(tinted);
        canvas.drawBitmap(src,0,0,null);
        Color c=new Color();
        p.setColor(c.argb(80,255,255,173));
        canvas.drawRect(r,p);
        tinted=adjustBrightness(tinted,1.1f);
        return tinted;*/

    }

    public static Bitmap sweetPurple(Bitmap src)
    {
        int w=src.getWidth();
        int h=src.getHeight();
        int [] pixels=new int[w*h];
        src.getPixels(pixels,0,w,0,0,w,h);

        double rf,gf,bf;
        int x,y;

        int r,g,b,p;
        rf=0.3522;
        gf=0.3522;
        bf=0.2956;
        for(int i=0;i<pixels.length;i++)
        {
            x=i%(w-1);
            y
            p=pixels[i];
            r=getRed(p);
            g=getGreen(p);
            b=getBlue(p);
            r=(int)((double)r*rf);
            g=(int)((double)g*gf);
            b=(int)((double)b*bf);
            pixels[i]=255<<24| (r)<<16 | g<<8 | b<<0;
        }
        src.setPixels(pixels,0,w,0,0,w,h);
        src=StylesLogic.adjustBrightness(src,2.8f);
        return  src;
    }
    public static Bitmap adjustBrightness(Bitmap original, float factor)
    {
        Bitmap adjusted = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        for(int x=0;x<original.getWidth();x++)
        {
            for(int y=0;y<original.getHeight();y++)
            {

                int pixel=original.getPixel(x, y);
                int alpha=getAlpha(pixel);
                int red=getRed(pixel);
                int green=getGreen(pixel);
                int blue=getBlue(pixel);
                int adred=(int) (red*factor);
                int adgreen=(int) (green*factor);
                int adblue=(int) (blue*factor);
                if(adred>255)
                    adred=255;
                if(adgreen>255)
                    adgreen=255;
                if(adblue>255)
                    adblue=255;
                Color c=new Color();
                adjusted.setPixel(x, y,c.argb(alpha, adred,adgreen, adblue));
            }
        }
        return adjusted;


    }

    public static Bitmap boxBlur(Bitmap src,int r)
    {
        Bitmap a= horizontalMotionBlur(src, r);
        Bitmap b=verticalMotionBlur(a, r);
        return b;
    }


    public static Bitmap gaussianBlur(Bitmap src, int r)
    {
        Bitmap boxblur1=boxBlur(src,r);
        Bitmap boxblur2=boxBlur(boxblur1, r);
        Bitmap boxblur3=boxBlur(boxblur2, r);
        return boxblur3;
    }

    public static Bitmap horizontalMotionBlur(Bitmap src, int r)
    {
        Bitmap a=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        int size=2*r+1;
        int w=src.getWidth();
        int h=src.getHeight();
        int rsum, gsum,bsum,ix,iy;
        int alpha = 255;
        int p,p1,p2;

        for(int y=0;y<h;y++)
        {
            rsum=gsum=bsum=0;
            for(int wx=-r;wx<=r;wx++)
            {
                ix=Math.min(w - 1, Math.max(0, wx));
                p=src.getPixel(ix, y);
                alpha=getAlpha(p);
                rsum+=getRed(p);
                gsum+=getGreen(p);
                bsum+=getBlue(p);
            }

            for(int x=0;x<w;x++)
            {
                int rval=rsum/size;
                int gval=gsum/size;
                int bval=bsum/size;

                Color c=new Color();
                a.setPixel(x,y,c.argb(alpha, rval, gval,bval));

                p1=src.getPixel(Math.max(0, x - r),y);
                p2=src.getPixel(Math.min(w-1,x+r+1), y);

                rsum+=getRed(p2)-getRed(p1);
                gsum+=getGreen(p2)-getGreen(p1);
                bsum+=getBlue(p2)-getBlue(p1);

            }
        }
        return a;
    }

    public static Bitmap verticalMotionBlur(Bitmap src, int r)
    {
        Bitmap a=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        int size=2*r+1;
        int w=src.getWidth();
        int h=src.getHeight();
        Log.v("imaging",""+ h);
        int rsum, gsum,bsum,ix,iy;
        int asum;
        int p,p1,p2;

        for(int x=w-1;x>=0;x--)
        {
            rsum=gsum=bsum=asum=0;
            for(int wy=-r;wy<=r;wy++)
            {
                iy=Math.min(h - 1, Math.max(0, wy));
                p=src.getPixel(x, iy);

                rsum+=getRed(p);
                gsum+=getGreen(p);
                bsum+=getBlue(p);
                asum+=getAlpha(p);
            }

            for(int y=0;y<h;y++)
            {
                int rval=rsum/size;
                int gval=gsum/size;
                int bval=bsum/size;
                int aval=asum/size;
                Color c=new Color();
                a.setPixel(x,y,c.argb(aval, rval, gval,bval));

                p1=src.getPixel(x,Math.max(0,y-r));
                p2 = src.getPixel(x, Math.min(h - 1, y + r + 1));

                rsum+=getRed(p2)-getRed(p1);
                gsum+=getGreen(p2)-getGreen(p1);
                bsum+=getBlue(p2)-getBlue(p1);
                asum+=getAlpha(p2)-getAlpha(p1);

            }
        }
        return a;
    }


    public static Bitmap tint(Bitmap src,double r, double g, double b)
    {
        Bitmap tinted=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        int p,red, green,blue, alpha;
        for(int x=0;x<src.getWidth();x++)
        {
            for(int y=0;y<src.getHeight();y++)
            {
                p=src.getPixel(x, y);
                red=getRed(p);
                green=getGreen(p);
                blue=getBlue(p);
                alpha=getAlpha(p);
                Color c=new Color();
                tinted.setPixel(x,y,c.argb(alpha,(int)(red*r), (int)(green*g), (int)(blue*b)));
            }
        }
        return tinted;
    }

    public static Bitmap gamma(Bitmap src, double gamma)
    {
        Bitmap a = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        int p,r,g,b,alpha;
        Color c=new Color();
        double[] val=new double[256];
        for(int i=0;i<val.length;i++)
        {
            val[i]=  Math.min(255,Math.max((255 * Math.pow(((double) i / 255), (1/gamma))), 0));
        }

        Log.v("imaging",""+val[90]);
        for(int x=0;x<src.getWidth();x++)
        {
            for(int y=0;y<src.getHeight();y++)
            {
                p=src.getPixel(x, y);
                r=getRed(p);
                g=getGreen(p);
                b=getBlue(p);
                alpha=getAlpha(p);
                a.setPixel(x,y,c.argb(alpha,(int)val[r],(int)val[g],(int)val[b]));
            }
        }
        return a;
    }


    public static Bitmap vignette(Bitmap src) {
        int ref=Math.max(src.getWidth(),src.getHeight());
        float radius = (float) (ref/1.2);
        RadialGradient gradient = new RadialGradient(src.getWidth()/2, src.getHeight()/2, radius, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);

        Bitmap image=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawARGB(1, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setShader(gradient);

        final Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());
        final Rect rectf = new Rect(rect);
        canvas.drawBitmap(src, rect, rect, null);
        canvas.drawRect(rectf, paint);


        return image;
    }

    public static Bitmap contrast(Bitmap src,int con)
    {
        int factor=((259)*(con+255))/((255)*(259-con));
        int [] val=new int[256];
        for(int i=0;i<val.length;i++)
        {
            val[i]=Math.min(255, Math.max(0,(factor*(i-128)+128)));
        }

        int p,r,g,b,alpha;
        Color c=new Color();
        Bitmap contrasted=Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        for(int x=0;x<src.getWidth();x++)
        {
            for(int y=0;y<src.getHeight();y++)
            {
                p=src.getPixel(x,y);
                r=getRed(p);
                g=getGreen(p);
                b=getBlue(p);
                alpha=getAlpha(p);
                contrasted.setPixel(x, y, c.argb(alpha, (int) val[r], (int) val[g], (int) val[b]));
            }
        }
        return contrasted;
    }

    /*public static Bitmap boxBlurOne(Bitmap src, int r)
    {
        Bitmap a=Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        int w=src.getWidth();
        int h=src.getHeight();
        int wh=w*h;
        int size=2*r+1;
        int wm=w-1;
        int hm=h-1;
        int px[]=new int[wh];
        src.getPixels(px,0,w,0,0,w,h);

        int [] red=new int[wh];
        int [] green=new int[wh];
        int [] blue=new int[wh];

        int redsum, greensum, bluesum, p,yi,yw, xi, x, y, p1, p2;
        int vmin[]=new int[Math.max(w,h)];
        int vmax[]=new int[Math.max(w,h)];



        yi=yw=0;
        for(y=0;y<h;y++)
        {
            redsum=greensum=bluesum=0;
            for(int i=-r;i<=r;i++)
            {
                p=px[yw+Math.min(wm, Math.max(0,i))] ;
                redsum+=getRed(p);
                Log.v("imaging",getRed(p)+" "+ redsum);
                greensum+=getGreen(p);
                bluesum+=getBlue(p);
            }

            for(x=0;x<w;x++)
            {
                red[yi]=redsum/size;
                Log.v("imaging", String.valueOf(red[yi]));
                green[yi]=greensum/size;
                blue[yi]=bluesum/size;

                if(y==0)
                {
                    vmin[x]=Math.max(0,x-r);
                    vmax[x]=Math.min(x+r+1,wm);
                }

                p1=px[yw+vmin[x]];
                p2=px[yw+vmax[x]];
                redsum+=getRed(p2)-getRed(p1);
                greensum+=getGreen(p2)-getGreen(p1);
                bluesum+=getBlue(p2)-getBlue(p1);

                yi++;
            }
            yw+=w;
        }
        a.setPixels(px,0,w,0,0,w,h);
        return a;
    }*/

    private static int getRed(int x)
    {
        return (x & 0xff0000)>>16;
    }
    private static int getGreen(int x)
    {
        return (x & 0x00ff00)>>8;
    }
    private static int getBlue(int x)
    {
        return (x&0xff);
    }
    private static int getAlpha(int x)
    {
        return (x>>24)&0xff;
    }

    private static double[] getHSL(int r, int g, int b)
    {
        double red,green,blue, min, max, L,S,H;
        H=0;
        red=r;
        green=g;
        blue=b;
        red/=255.0;
        green/=255.0;
        blue/=255.0;
        min=Math.min(red, Math.min(green, blue));
        max=Math.max(red, Math.max(green, blue));

        L=(min+max)/2;

        if(L<0.5)
            S=(max-min)/(max+min);
        else
            S=(max-min)/(2-max-min);

        if(max!=min)
        {
            if(red==max)
            {
                H=(green-blue)/(max-min);
            }
            if(green==max)
            {
                H=2.0+ (blue-red)/(max-min);
            }
            if(blue==max)
            {
                H=4.0+ (red-green)/(max-min);
            }
        }
        else
            H=0;

        H=60*H;
        if(H<0)
            H+=360;

        double[] HSL={H,S,L};
        return HSL;

    }


}

