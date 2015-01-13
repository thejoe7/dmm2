package com.joewuq.dmm.utility;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

import com.joewuq.dmm.R;

import java.security.InvalidParameterException;
import java.util.Random;

/**
 * Created by Joe Wu on 1/10/15.
 */
public class Utility {
    public static int randInt(int min, int max) {
        if (min > max) {
            throw new InvalidParameterException("randInt(int, int) min must be smaller or equal to max.");
        } else if (min == max) {
            return min;
        } else {
            return new Random().nextInt((max - min) + 1) + min;
        }
    }

    public static ObjectAnimator getElevationAnimator(Context context, Object object, float from_elevation, float to_elevation) {
        return getElevationAnimator(context, object, from_elevation, to_elevation, null);
    }

    public static ObjectAnimator getElevationAnimator(Context context, Object object, float from_elevation, float to_elevation, TimeInterpolator interpolator) {
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        ObjectAnimator elevation = ObjectAnimator.ofFloat(object, "elevation", from_elevation, to_elevation)
                .setDuration(context.getResources().getInteger(android.R.integer.config_shortAnimTime));
        elevation.setInterpolator(interpolator);
        return elevation;
    }

    public static int getThemeColorResourceId(ThemeColor themeColor) {
        switch (themeColor) {
            case RED:
                return R.color.theme_red;
            case PINK:
                return R.color.theme_pink;
            case PURPLE:
                return R.color.theme_purple;
            case INDIGO:
                return R.color.theme_indigo;
            case BLUE:
                return R.color.theme_blue;
            case CYAN:
                return R.color.theme_cyan;
            case TEAL:
                return R.color.theme_teal;
            case GREEN:
                return R.color.theme_green;
            case YELLOW:
                return R.color.theme_yellow;
            case ORANGE:
                return R.color.theme_orange;
            default:
                return R.color.theme_default;
        }
    }

    public static int getThemeDarkColorResourceId(ThemeColor themeColor) {
        switch (themeColor) {
            case RED:
                return R.color.theme_red_dark;
            case PINK:
                return R.color.theme_pink_dark;
            case PURPLE:
                return R.color.theme_purple_dark;
            case INDIGO:
                return R.color.theme_indigo_dark;
            case BLUE:
                return R.color.theme_blue_dark;
            case CYAN:
                return R.color.theme_cyan_dark;
            case TEAL:
                return R.color.theme_teal_dark;
            case GREEN:
                return R.color.theme_green_dark;
            case YELLOW:
                return R.color.theme_yellow_dark;
            case ORANGE:
                return R.color.theme_orange_dark;
            default:
                return R.color.theme_default_dark;
        }
    }

    public static int getThemeResourceId(ThemeColor themeColor) {
        switch (themeColor) {
            case RED:
                return R.style.DmmTheme_Red;
            case PINK:
                return R.style.DmmTheme_Pink;
            case PURPLE:
                return R.style.DmmTheme_Purple;
            case INDIGO:
                return R.style.DmmTheme_Indigo;
            case BLUE:
                return R.style.DmmTheme_Blue;
            case CYAN:
                return R.style.DmmTheme_Cyan;
            case TEAL:
                return R.style.DmmTheme_Teal;
            case GREEN:
                return R.style.DmmTheme_Green;
            case YELLOW:
                return R.style.DmmTheme_Yellow;
            case ORANGE:
                return R.style.DmmTheme_Orange;
            default:
                return R.style.DmmTheme_Default;
        }
    }
}
