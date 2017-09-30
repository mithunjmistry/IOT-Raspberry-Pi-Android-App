package com.mithu.mithunmistry.mithun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;


import java.util.logging.Handler;
import java.util.logging.LogRecord;

//import pl.droidsonroids.gif.GifImageView;

public class animationGif extends AppCompatActivity {
    //GifImageView micky2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_gif);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //micky2 = (GifImageView) findViewById(R.id.view);


    }
}
