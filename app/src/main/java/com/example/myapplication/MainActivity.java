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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textX, textY, textZ;
    private ImageView image;
    private ProgressBar progressBarX, progressBarY, progressBarZ;
    private Button buttonToNumbers;
    private SensorManager sensorManager;
    Sensor accelerometer;
    private float movementPerSecond = 12.0f;
    private float noMovementPerSecond = 0.0f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Toast.makeText(this, "No sensor found", Toast.LENGTH_LONG).show();
            finish();
        }else{
            sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        image = findViewById(R.id.imageView);
        image.setVisibility(View.GONE);

        progressBarX = (ProgressBar) findViewById(R.id.progressBarX);
        progressBarX.setMax(20);
        progressBarY = (ProgressBar) findViewById(R.id.progressBarY);
        progressBarY.setMax(20);
        progressBarZ = (ProgressBar) findViewById(R.id.progressBarZ);
        progressBarZ.setMax(20);

        textX = findViewById(R.id.textView1);
        textY = findViewById(R.id.textView2);
        textZ = findViewById(R.id.textView3);

        buttonToNumbers = findViewById(R.id.button);
        buttonToNumbers.setText("See Actual Numbers");
        buttonToNumbers.setBackgroundColor(Color.rgb(0,0,0));
        buttonToNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent A2 = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(A2);
                Toast.makeText(MainActivity.this, "Switched activity", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }//on create over


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            textX.setText("X: "); textY.setText("Y: "); textZ.setText("Z: ");

            int progressX = (int) ((x + 10) * (progressBarX.getMax() / 20.0));
            progressBarX.setProgress(progressX);
            int progressY = (int) ((y + 10) * (progressBarY.getMax() / 20.0));
            progressBarY.setProgress(progressY);
            int progressZ = (int) ((z + 10) * (progressBarZ.getMax() / 20.0));
            progressBarZ.setProgress(progressZ);

            float accelerationMagnitude = (float) Math.sqrt(x * x + y * y + z * z);

            if (accelerationMagnitude >= movementPerSecond) {
                Toast.makeText(MainActivity.this, "Calm down!", Toast.LENGTH_SHORT).show();
                image.setVisibility(View.VISIBLE);
            } else if (accelerationMagnitude < noMovementPerSecond) { //this is not functioning
                image.setVisibility(View.INVISIBLE);
            }
        }
    } //onchange over

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }//on accuracy over

    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}