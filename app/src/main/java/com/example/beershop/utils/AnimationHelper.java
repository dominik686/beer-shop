package com.example.beershop.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AnimationHelper {
    public static void shake(View v) {
        YoYo.with(Techniques.Shake).duration(300).playOn(v);
    }

    public static void bounce(View v) {
        YoYo.with(Techniques.Bounce).duration(700).playOn(v);
    }

    public static void rubberBand(View v) {
        YoYo.with(Techniques.RubberBand).duration(700).playOn(v);

    }
}
