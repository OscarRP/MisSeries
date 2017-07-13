package com.example.oscarruiz.misseries.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.example.oscarruiz.misseries.R;

/**
 * Created by Carlos Ruiz on 06/07/2017.
 */

public class Animations {

    /**
     * Animation
     */
    private Animation animation;


    /**
     * Starts left to right animnation
     */
    public void leftToRightAnimation (View view, Context context, int duration) {
        animation = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * Starts right to left animnation
     */
    public void rightToLeftAnim (View view, Context context, int duration) {
        animation = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * Starts down to up animation
     */
    public void downToUp (View view, Context context, int duration) {
        animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * Starts up to down animation
     */
    public void upToDown (View view, Context context, int duration) {
        animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * Starts up to alpha_in animation
     */
    public void alphaIn (View view, Context context, int duration) {
        animation = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * Starts Home Animations
     */
    public void homeAnimations (View view1, View view2, final View view3, final Context context) {
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);


        leftToRightAnimation(view1, context, 500);

        animation = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        animation.setDuration(500);
        view2.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view3.setVisibility(View.VISIBLE);
                downToUp(view3, context, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * Starts splash Animations
     */
    public void splashAnimations (final View postersLayout, final View logo, final View dexter, final View breaking, final View got, final View walking, final Context context) {
        leftToRightAnimation(dexter, context, 1500);
        rightToLeftAnim(walking, context, 1500);
        downToUp(got, context, 1500);

        animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down);
        animation.setDuration(1500);
        breaking.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
                animation.setDuration(2000);
                postersLayout.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dexter.setVisibility(View.GONE);
                        breaking.setVisibility(View.GONE);
                        got.setVisibility(View.GONE);
                        walking.setVisibility(View.GONE);
                        logo.setVisibility(View.VISIBLE);
                        alphaIn(logo, context, 3000);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
