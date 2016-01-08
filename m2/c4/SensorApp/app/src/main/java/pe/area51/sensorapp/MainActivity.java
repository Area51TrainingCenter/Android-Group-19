package pe.area51.sensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private TextView accelerometerXTextView;
    private TextView accelerometerYTextView;
    private TextView accelerometerZTextView;

    private boolean isAccelerometerStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isAccelerometerStarted = false;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerXTextView = (TextView) findViewById(R.id.textview_accelerometer_x);
        accelerometerYTextView = (TextView) findViewById(R.id.textview_accelerometer_y);
        accelerometerZTextView = (TextView) findViewById(R.id.textview_accelerometer_z);

        findViewById(R.id.button_toggle_accelerometer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAccelerometerStarted()) {
                    stopAccelerometer();
                } else {
                    startAccelerometer();
                }
            }
        });
    }

    private void startAccelerometer() {
        final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        isAccelerometerStarted = true;
    }

    private void stopAccelerometer() {
        sensorManager.unregisterListener(this);
        isAccelerometerStarted = false;
    }

    private boolean isAccelerometerStarted() {
        return isAccelerometerStarted;
    }

    private void showAccelerometerEvent(final SensorEvent sensorEvent) {
        final float accelerometerX = sensorEvent.values[0];
        final float accelerometerY = sensorEvent.values[1];
        final float accelerometerZ = sensorEvent.values[2];

        accelerometerXTextView.setText(getString(R.string.x_axis, String.valueOf(accelerometerX)));
        accelerometerYTextView.setText(getString(R.string.y_axis, String.valueOf(accelerometerY)));
        accelerometerZTextView.setText(getString(R.string.z_axis, String.valueOf(accelerometerZ)));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            showAccelerometerEvent(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
