package fdnascimento.com.liosensormanager;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private  Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize SensorManager
        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //initialize Accelerometer using SensorManager
        this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


//         List all sensor device enable on device, only for sample.
        PackageManager manager = getPackageManager();
        SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorMngr.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensors) {
            Log.v("Sensor","Sensor:"+sensor.getName().toString());
        }
    }

    protected void onResume() {
        super.onResume();
        //We register Acceleromter for SensorMagnager using SENSOR_DELAY_NORMAL on this case
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        // We unregisterListener for this Activity.
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Verify the level of accelerometer and apply my configuration.
        if ((int)event.values[1] <= -2){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        }else if ((int)event.values[1] >= 0){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nice to have all implement all Listener, but not necessary.
    }
}
