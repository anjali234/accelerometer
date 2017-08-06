package com.example.user.accelerometer;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    GraphView graph;
    private LineGraphSeries<DataPoint> mSeries;
    int count =0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        graph = (GraphView) findViewById(R.id.graph);
        Viewport gview =  graph.getViewport();
        gview.setXAxisBoundsManual(true);
        gview.setMinX(0);
        gview.setScrollable(true);
        gview.setMinY(0);
        gview.setScalable(true);
        // activate horizontal and vertical zooming and scrolling
        gview.setScalableY(true);
        // activate vertical scrolling
        gview.setScrollableY(true);
        mSeries = new LineGraphSeries<>();
        //mseries features
        mSeries.setTitle("Acceleration curve");
        mSeries.setColor(Color.BLUE);
        mSeries.setDrawDataPoints(true);
        mSeries.setDataPointsRadius(7);
        mSeries.setThickness(5);
        graph.addSeries(mSeries);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x,y,z,value;
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            x=sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            value=(float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
            count++;
            mSeries.appendData(new DataPoint(count,value), true, 500);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStartClick(View v){
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStopClick(View v){
        senSensorManager.unregisterListener((SensorEventListener)this);
    }
}