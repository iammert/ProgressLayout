package co.mobiwise.library;

import android.graphics.Color;
import android.support.annotation.ColorInt;

class Util {
    /**
     * Add transparency to an color
     * @param color the color to return
     * @param ratio transparency ratio
     * @return the color with the transparency added
     */
    static int getColorWithAlpha(@ColorInt int color, float ratio) {
        if (ratio < 0.0 || ratio > 1.0) {
            throw new IllegalArgumentException("alphaColor must not be greater than 1.0 or less than 0.0, you supplied " + ratio);
        }
        int alpha = Math.round(Color.alpha(color) * ratio);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
