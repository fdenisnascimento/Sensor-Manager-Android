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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean busy = false;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize SensorManager
        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //initialize Accelerometer using SensorManager
        this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        // List all sensor device enable on device, only for sample.
        PackageManager manager = getPackageManager();
        SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorMngr.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : sensors) {
            Log.v("Sensor","Sensor:"+sensor.getName().toString());
        }
        relativeLayout = (RelativeLayout) getWindow().getDecorView().findViewById(R.id.activity_main);
    }

    private void animateViewPayment() {

    }

    private void upView(){

        if (busy) return;

        busy = true;

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        slide_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                busy = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeLayout.startAnimation(slide_up);

    }

    private void downView(){

        if (busy) return;

        busy = true;

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_down.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            busy = false;
            upView();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    });

        relativeLayout.startAnimation(slide_down);

    }

    protected void onResume() {
        super.onResume();
        //We register Acceleromter for SensorManager using SENSOR_DELAY_NORMAL on this case
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
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            downView();

        }else if ((int)event.values[1] >= 0){
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            downView(false);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nice to have all implement all Listener, but not necessary.
    }
}
