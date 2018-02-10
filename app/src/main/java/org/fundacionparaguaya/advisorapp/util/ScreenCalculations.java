package org.fundacionparaguaya.advisorapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Functions for handling different screen sizes
 */

public class ScreenCalculations {

    public static boolean is7InchTablet(Context context)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        return (convertsPixelsToDp(metrics.heightPixels, context) < 600);
    }
    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertsPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}