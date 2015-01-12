package com.joewuq.dmm.utility;

import android.util.Log;

import com.joewuq.dmm.R;

/**
 * Created by Joe Wu on 1/10/15.
 */
public class Utility {
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
}
