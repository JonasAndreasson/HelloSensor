package com.example.hellosensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.hardware.SensorEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.os.Bundle;


public class DisplayCompassActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager SensorManage;
    private ImageView compassImage;
    private View root;
    private float DegreeStart = 0f;
    TextView DegreeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_compass);

        compassImage = (ImageView) findViewById(R.id.compass_image);

        DegreeTV = (TextView) findViewById(R.id.DegreeTV);
        root = findViewById(R.id.screen).getRootView();
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        DegreeTV.setText("Riktning: "+Float.toString(degree) +" grader");

        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        ra.setFillAfter(true);

        ra.setDuration(20);

        compassImage.startAnimation(ra);

        DegreeStart = -degree;

        if(degree<=15 || degree >= 345){
            root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        } else {
            root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
        }

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
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}