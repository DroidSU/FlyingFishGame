package com.morningstar.flyingfishgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView fishView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishView = new FlyingFishView(this);
        setContentView(fishView);
    }
}
