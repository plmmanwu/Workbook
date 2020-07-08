package com.wlwoon.base.common;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by wxy on 2020/7/8.
 */

public class BitmapUtil {

    /**
     * bitmap 是否是亮图
     * @param bitmap
     * @return
     */
    public static boolean isBitmapBright(Bitmap bitmap) {
        float hitCount = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = bitmap.getPixel(i, j);
                // Calculate gray value (RGB -> YUV)
                if (isBright(color)) {
                    hitCount++;
                }
            }
        }
        return (hitCount / (width * height)) > 0.5;
    }

    private static boolean isBright(int color) {
        double grayLevel = (Color.red(color) * 30 + Color.green(color) * 59
                + Color.blue(color) * 11 ) / 100;
        return grayLevel >= 215;
    }

}
