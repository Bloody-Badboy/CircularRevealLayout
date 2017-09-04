package com.bloodybadboycreation.circularreveallayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * Created by BloodyBadboyCreation on 02-09-2017.
 */

public class CircularRevelLayout extends FrameLayout {

    public static final Property<CircularRevelLayout, Integer> CLIP_CIRCLE_RADIUS_PROGRESS =
            new Property<CircularRevelLayout, Integer>(Integer.class, "clipCircleRadius") {
                @Override
                public Integer get(CircularRevelLayout view) {
                    return view.getClipCircleRadius();
                }

                @Override
                public void set(CircularRevelLayout object, Integer value) {
                    object.setClipCircleRadius(value);
                }

            };
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private Path clipPath = new Path();
    private int revealX = 0;
    private int revealY = 0;
    private int startRadius = 0;
    private int finalRadius;
    private int clipCircleRadius = 0;
    private int duration = 400;
    private boolean isReveled = false;
    private CallBacks listener;

    public CircularRevelLayout(Context context) {
        super(context);
    }

    public CircularRevelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularRevelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(CallBacks listener) {
        this.listener = listener;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        finalRadius = (int) Math.hypot(getWidth(), getHeight());
    }

    public void revealFrom(int revealX, int revealY,int startRadius) {
        this.revealX = revealX;
        this.revealY = revealY;
        this.startRadius = startRadius;

        ObjectAnimator revelAnimator = ObjectAnimator.ofInt(CircularRevelLayout.this, CLIP_CIRCLE_RADIUS_PROGRESS, startRadius, finalRadius);
        revelAnimator.setInterpolator(ACCELERATE_INTERPOLATOR);
        revelAnimator.setDuration(duration);
        revelAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onRevealed();
                }
            }
        });
        revelAnimator.start();
    }

    public void unReveal() {
        ObjectAnimator unRevelAnimator = ObjectAnimator.ofInt(CircularRevelLayout.this, CLIP_CIRCLE_RADIUS_PROGRESS, finalRadius, startRadius);
        unRevelAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        unRevelAnimator.setDuration(duration);
        unRevelAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onUnRevealed();
                }
            }
        });
        unRevelAnimator.start();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        clipPath.reset();
        clipPath.moveTo(revealX, revealY);
        clipPath.addCircle(revealX, revealY, clipCircleRadius, Path.Direction.CW);
        clipPath.close();

        canvas.clipPath(clipPath);

        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public synchronized int getClipCircleRadius() {
        return clipCircleRadius;
    }

    public synchronized void setClipCircleRadius(int clipCircleRadius) {
        this.clipCircleRadius = clipCircleRadius;
        invalidate();
    }

    public interface CallBacks {
        void onRevealed();

        void onUnRevealed();
    }
}
