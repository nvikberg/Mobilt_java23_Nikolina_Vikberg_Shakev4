package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {

    private Button buttonSwitchBack;
    private TextView textView;
    private SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            Toast.makeText(this, "No proximity sensor found", Toast.LENGTH_LONG).show();
            finish();
        }else{
            sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }



        textView = findViewById(R.id.textView);
        buttonSwitchBack = findViewById(R.id.button2);
        buttonSwitchBack.setBackgroundColor(Color.rgb(0,0,0));
        buttonSwitchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent A2 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(A2);
                Toast.makeText(MainActivity2.this, "Back to home", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            textView.setText("X: " + x + "\n\nY: " + y + "\n\nZ: " + z);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}