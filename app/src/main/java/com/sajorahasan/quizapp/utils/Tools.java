package com.sajorahasan.quizapp.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.sajorahasan.quizapp.R;
import com.tapadoo.alerter.Alerter;

/**
 * Created by Sajora on 27-12-2017.
 */

public class Tools {

    public static void rippleBlack(View view) {
        MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleHover(true)
                .rippleAlpha(0.2F)
                .rippleColor(0xFF585858)
                .create();
    }

    public static void rippleWhite(View view) {
        MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleHover(true)
                .rippleAlpha(0.2F)
                .rippleColor(Color.WHITE)
                .create();
    }

    public static void alert(Activity activity, String title, String msg) {
        Alerter.create(activity)
                .setTitle(title)
                .setText(msg)
                .show();
    }

    public static void alertError(Activity activity, String msg) {
        Alerter.create(activity)
                .setTitle("Error")
                .setText(msg)
                .setBackgroundColorRes(R.color.red)
                .show();
    }

    public static int getCategoryValue(String category) {
        category = category.replaceAll("[:]", "");
        if (category.equalsIgnoreCase("General Knowledge")) {
            return 9;
        } else if (category.equalsIgnoreCase("Entertainment Books")) {
            return 10;
        } else if (category.equalsIgnoreCase("Entertainment Film")) {
            return 11;
        } else if (category.equalsIgnoreCase("Entertainment Music")) {
            return 12;
        } else if (category.equalsIgnoreCase("Entertainment Musicals & Theatres")) {
            return 13;
        } else if (category.equalsIgnoreCase("Entertainment Television")) {
            return 14;
        } else if (category.equalsIgnoreCase("Entertainment Video Games")) {
            return 15;
        } else if (category.equalsIgnoreCase("Entertainment Board Games")) {
            return 16;
        } else if (category.equalsIgnoreCase("Science & Nature")) {
            return 17;
        } else if (category.equalsIgnoreCase("Science Computers")) {
            return 18;
        } else if (category.equalsIgnoreCase("Science Mathematics")) {
            return 19;
        } else if (category.equalsIgnoreCase("Mythology")) {
            return 20;
        } else if (category.equalsIgnoreCase("Sports")) {
            return 21;
        } else if (category.equalsIgnoreCase("Geography")) {
            return 22;
        } else if (category.equalsIgnoreCase("History")) {
            return 23;
        } else if (category.equalsIgnoreCase("Politics")) {
            return 24;
        } else if (category.equalsIgnoreCase("Art")) {
            return 25;
        } else if (category.equalsIgnoreCase("Celebrities")) {
            return 26;
        } else if (category.equalsIgnoreCase("Animals")) {
            return 27;
        } else if (category.equalsIgnoreCase("Vehicles")) {
            return 28;
        } else if (category.equalsIgnoreCase("Entertainment Comics")) {
            return 29;
        } else if (category.equalsIgnoreCase("Science Gadgets")) {
            return 30;
        } else if (category.equalsIgnoreCase("Entertainment Japanese Anime & Manga")) {
            return 31;
        } else if (category.equalsIgnoreCase("Entertainment Cartoon & Animations")) {
            return 32;
        }
        return 0;
    }

}
