package com.cwgreene.testlocation.activities;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cwgreene.testlocation.R;
import com.cwgreene.testlocation.loggers.AccelerometerLogger;
import com.cwgreene.testlocation.loggers.GyroLogger;
import com.cwgreene.testlocation.loggers.SensorLogger;
import com.cwgreene.testlocation.loggers.TriggerSensorLogger;
import com.cwgreene.testlocation.loggers.WifiLogger;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class SensorActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private WifiLogger mWifiLogger;
    private LinearLayout linearLayout;
    private long startTime;

    private void registerSensors() {
        startTime = System.currentTimeMillis();
        Function<String, Function<float[], Void>> generate = (name) -> {
            TextView tv = new TextView(this);
            tv.setText(String.format("[%s]:",name));
            final AtomicLong count = new AtomicLong(0);
            if(tv == null) {
                Log.e("Doom", "tv is null");
            }
            if(linearLayout == null) {
                Log.e("Doom", "linear layout is null");
            }
            linearLayout.addView(tv);
            Function<float[], Void> func = (values) -> {
                count.addAndGet(1);
                double elapsed = (System.currentTimeMillis() - startTime) / 1000.0;
                tv.setText(String.format("[%s] Updates / second: %f", name, (count.get() * 1.0) / elapsed));
                return null;
            };
            return func;
        };
        mSensorManager = getSystemService(SensorManager.class);
        mSensorManager.registerListener(new SensorLogger("Accelerometer",
                        generate.apply("Accelerometer")),
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(new SensorLogger("Linear Acceleration",
                        generate.apply("Linear Acceleration")),
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(new SensorLogger("Gyroscope",
                        generate.apply("Gyroscope")),
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(new SensorLogger("Magnetic Field",
                        generate.apply("Magenetic Field")),
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED),
                SensorManager.SENSOR_DELAY_FASTEST);
        Sensor sigMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        if (sigMotion != null) {
            mSensorManager.requestTriggerSensor(new TriggerSensorLogger("Significant Motion",
                            generate.apply("Significant Motion")),
                    sigMotion);
        }
        Sensor motion = mSensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        if (motion != null) {
            mSensorManager.requestTriggerSensor(new TriggerSensorLogger("Motion",
                            generate.apply("Motion")),
                    mSensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT));
        }
        mWifiLogger = new WifiLogger("WifiLogger", this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        linearLayout = findViewById(R.id.sensorLayout);
        if (linearLayout == null) {
            Log.e("Doom", "doom");
        }
        registerSensors();
    }
}
