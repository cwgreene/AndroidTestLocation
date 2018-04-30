package com.cwgreene.testlocation.loggers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.function.Function;

public class RotationLogger extends SensorLogger implements SensorEventListener {
    String TAG = RotationLogger.class.toString();

    public RotationLogger(String sensor, Function<float[], Void> callback) {
        super(sensor, callback);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i(sensorName, String.format("x: %f y: %f z: %f, s: %f",
                sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2],
                sensorEvent.values[3]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, String.format("Sensor (%s) changed accuracy to %d",
                sensor.toString(), accuracy));
    }
}