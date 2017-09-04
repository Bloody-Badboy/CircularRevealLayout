package com.bloodybadboycreation.circularreveallayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mActivityIntent = new Intent(MainActivity.this, RevealActivity.class);
                int[] outLocation = new int[2];
                view.getLocationOnScreen(outLocation);
                outLocation[0] += view.getWidth() / 2;
                mActivityIntent.putExtra("revealX", outLocation[0]);
                mActivityIntent.putExtra("revealY", outLocation[1]);
                startActivity(mActivityIntent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
