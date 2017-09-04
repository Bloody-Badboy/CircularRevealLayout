package com.bloodybadboycreation.circularreveallayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;

public class RevealActivity extends AppCompatActivity implements CircularRevelLayout.CallBacks {

    private CircularRevelLayout mCircularRevealLayout;
    private int revealX, revealY;
    private boolean pendingAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCircularRevealLayout = (CircularRevelLayout) findViewById(R.id.circular_reveal);
        mCircularRevealLayout.setDuration(700);
        mCircularRevealLayout.setListener(this);

        if (savedInstanceState == null) {
            revealX = getIntent().getIntExtra("revealX", 0);
            revealY = getIntent().getIntExtra("revealY", 0);

            mCircularRevealLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mCircularRevealLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    mCircularRevealLayout.revealFrom(revealX, revealY, 0);
                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (!pendingAnimation) {

            mCircularRevealLayout.unReveal();
        }
    }

    @Override
    public void onRevealed() {

    }

    @Override
    public void onUnRevealed() {
        pendingAnimation = false;
        finish();
    }
}
