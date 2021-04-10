package com.example.beershop.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AnimationHelper {
    public static void shake(View v) {
        YoYo.with(Techniques.Shake).duration(300).repeat(1).playOn(v);
    }
}
