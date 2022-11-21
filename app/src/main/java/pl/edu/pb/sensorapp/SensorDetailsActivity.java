package pl.edu.pb.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView sensorValue;
    private TextView sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);
        int type = getIntent().getIntExtra("Type",Sensor.TYPE_LIGHT);

        sensorType = findViewById(R.id.sensor_type_text);
        sensorValue = findViewById(R.id.sensor_value_text);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor= sensorManager.getDefaultSensor(type);

        if(sensor == null)
        {
            sensorValue.setText(R.string.missing_sensor);
        }else
        {
            sensorType.setText(sensor.getName());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sensor != null)
        {
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sensorValue.setText(sensorEvent.values[0] + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}