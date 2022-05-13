package com.example.hellosensor;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayAccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager SensorManage;
    TextView valueTV, directionTV;
    ImageView arrowIV;
    String direction;
    final int LEFT = 20;
    final int RIGHT = -LEFT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_accelerometer);
        valueTV = (TextView) findViewById(R.id.valueTV);
        directionTV = (TextView) findViewById(R.id.directionTV);
        directionTV.setTextColor(Color.BLACK);
        arrowIV = (ImageView) findViewById(R.id.arrow);
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        View someView = findViewById(R.id.screen);
        View root = someView.getRootView();
        root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
//      direction = "Höger";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];


        if (arrowIV.getRotation()%360 == 0){
            direction = "Höger";
        } else if (arrowIV.getRotation()%360 == 180){
            direction = "Vänster";
        }
        directionTV.setText(direction);
        if(x>=LEFT && direction.equals("Vänster")){
            flipArrow();
            Log.v("Output", "x: "+ x
                    + "\ny: " +y + "\nz: "+ z);
        }
        if(RIGHT>=x && direction.equals("Höger")){
            arrowIV.setRotation(arrowIV.getRotation()+180);
            Log.v("Output", "x: "+ x
                    + "\ny: " +y + "\nz: "+ z);
        }

    }
    private void flipArrow(){
        arrowIV.setRotation(arrowIV.getRotation()+180);
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