package com.example.hellosensor;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayAccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SensorManage;
    TextView valueTV, directionTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_accelerometer);
        valueTV = (TextView) findViewById(R.id.valueTV);
        directionTV = (TextView) findViewById(R.id.directionTV);
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            valueTV.setTextColor(Color.BLACK);
            valueTV.setText("Accelerometer values: \n"+
                    "x: "+ Math.round(x)+"\n" +
                    "y: " + Math.round(y)+"\n" +
                    "z: "+ Math.round(z)
            );
        View someView = findViewById(R.id.screen);
        View root = someView.getRootView();
        root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
//        if(x>8) root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple));
//        else if(z>8) root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_bright));
//        else if(y>8) root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
//        else root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
        directionTV.setTextColor(Color.BLACK);
        if(x>=5) directionTV.setText("Vänster");
        if(-5>x) directionTV.setText("Höger");
        if(-5<x&&x<5) directionTV.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        SensorManage.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // code for system's orientation sensor registered listeners
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}