package com.mithu.mithunmistry.mithun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ParameterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Developed by Mithun", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
            }
        });
    }

    public void temperature(View view){
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
    }

    public void humidity(View view){
        Intent intent = new Intent(this, HumidityActivity.class);
        startActivity(intent);
    }

    public void level(View view){
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }

    public void moisture(View view){
        Intent intent = new Intent(this, MoistureActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

}
