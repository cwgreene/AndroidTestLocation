package com.cwgreene.testlocation.loggers;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class SensorLogger implements SensorEventListener {

    private final Function<float[], Void> callback;
    int currentCount;

    public static void LogSensors(Activity activity) {
        SensorManager sensorManager = (SensorManager)
                activity.getSystemService(Context.SENSOR_SERVICE);
        for (Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            Log.i("SENSOR_MANAGER", s.toString());
        }
    }

    protected String sensorName;

    public SensorLogger(String sensor, Function<float[], Void> callback) {
        currentCount = 0;
        sensorName = sensor;
        if (callback == null) {
            this.callback = (id) -> null;
        } else {
            this.callback = callback;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i(sensorName, Arrays.toString(sensorEvent.values));
        callback.apply(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(sensorName, String.format("Sensor (%s) changed accuracy to %d",
                sensor.toString(), accuracy));
    }
}
