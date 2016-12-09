package com.example.madooding.healthpy.utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

/**
 * Created by madooding on 12/7/2016 AD.
 */

public class CircularTransformation implements Transformation {

    /**
     * Size of the target bitmap to generate a squared image
     */
    private int size;
    /**
     * Radius of the image
     */
    private int radius;


    @Override
    public Bitmap transform(Bitmap source) {
        size = source.getWidth();
        radius = (int) Math.ceil(size / 2);
        Bitmap output = Bitmap.createBitmap(size,
                size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, source.getWidth(),
                source.getHeight());
        final Rect target = new Rect(0, 0, size, size);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(radius, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, target, paint);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "radius" + size;
    }
}
