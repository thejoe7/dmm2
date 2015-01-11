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
                return 0;
        }
    }
}
