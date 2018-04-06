package com.ising99.intelligentremotecontrol.component;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

/**
 * AnimationPlayer. Plays animation frames sequence in loop
 */
public class FramesSequenceAnimation {
    private FramesSequenceAnimationDelegate delegate;
    private int[] frames;
    private int currentIndex;
    private boolean shouldBeRun; // true if the animation should continue running. Used to stop the animation
    private boolean isRunning; // true if the animation currently running. prevents starting the animation twice
    private SoftReference<ImageView> softReference; // Used to prevent holding ImageView when it should be dead.
    private Handler handler;
    private int timeInterval;


    private Bitmap mBitmap = null;
    private BitmapFactory.Options mBitmapOptions;

    @SuppressLint("ObsoleteSdkInt")
    public FramesSequenceAnimation(ImageView imageView, int[] resIDs, int fps) {
        handler = new Handler();
        frames = resIDs;
        currentIndex = -1;
        softReference = new SoftReference<>(imageView);
        shouldBeRun = false;
        isRunning = false;
        timeInterval = 1000 / fps;

        imageView.setImageResource(frames[0]);

        // use in place bitmap to save GC work (when animation images are the same size & type)
        if (Build.VERSION.SDK_INT >= 11) {
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            Bitmap.Config config = bmp.getConfig();
            mBitmap = Bitmap.createBitmap(width, height, config);
            mBitmapOptions = new BitmapFactory.Options();
            // setup bitmap reuse options.
            mBitmapOptions.inBitmap = mBitmap;
            mBitmapOptions.inMutable = true;
//            mBitmapOptions.in
            mBitmapOptions.inSampleSize = 1;
        }
    }



    private int getNext() {
        currentIndex++;
        if (currentIndex >= frames.length)
            currentIndex = 0;
        return frames[currentIndex];
    }

    /**
     * Starts the animation
     */
    public synchronized void start() {
        shouldBeRun = true;
        if (isRunning) return;
        if (delegate != null) delegate.didStartAnimation();
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ImageView imageView = softReference.get();
                if (!shouldBeRun || imageView == null) {
                    isRunning = false;
                    if (delegate != null) delegate.didStopAnimation();
                    return;
                }

                isRunning = true;
                handler.postDelayed(this, timeInterval);

                if (imageView.isShown()) {
                    int imageRes = getNext();
                    if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                        Bitmap bitmap = null;
                        try {

//                            bitmap  = decodeSampledBitmapFromResource(imageView.getResources(),imageRes,mBitmapOptions.outWidth,mBitmapOptions.outHeight);
                            bitmap = BitmapFactory.decodeResource(imageView.getResources(), imageRes, mBitmapOptions);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        } else {
                            imageView.setImageResource(imageRes);
                            mBitmap.recycle();
                            mBitmap = null;
                        }
                    } else {
                        imageView.setImageResource(imageRes);
                    }
                }

            }
        };

        handler.post(runnable);
    }

    /**
     * Stops the animation
     */
    public synchronized void stop() {
        shouldBeRun = false;
    }

    public void setDelegate(FramesSequenceAnimationDelegate delegate) {
        this.delegate = delegate;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
