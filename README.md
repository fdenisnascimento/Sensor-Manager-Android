# Sensor Manager Android

A simple sample of use SensorManager with android for change orientation your device

### Prerequisites

You device needs has Accelerometer, Accelerometer isn't a software he's a hardware.
For check if you device has this hardware, execute the code below you can execute this code onCreate of your activity.

    PackageManager manager = getPackageManager();
    SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    List<Sensor> sensors = sensorMngr.getSensorList(Sensor.TYPE_ALL);

    for (Sensor sensor : sensors) {
        Log.v("Sensor","Sensor:"+sensor.getName().toString());
    }

## Getting Started
### 1st
	
Make your Activity implement SensorEventListener, and create two variables.
a for to control SensorManager and other for to control Accelerometer, see below.


	public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    
    
### 2nd

 onCreate of you activity

	// initialize SensorManager
	   this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	
	//initialize Accelerometer using SensorManager
	  this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   
	  
###3rd

We register Acceleromter for SensorMagnager using SENSOR_DELAY_NORMAL on this case

    protected void onResume() {
        super.onResume();     
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }	    
    
### 4th   
  
  We unregisterListener for this Activity.
  
    protected void onPause() {
       	super.onPause();
     	mSensorManager.unregisterListener(this);
    }
    
    
###5th

You need implement Listener onSensorChanges.

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Verify the level of accelerometer and apply my configuration.
        if ((int)event.values[1] <= -2){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        }else if ((int)event.values[1] >= 0){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

###6th

You need implement Listener onAccuracyChanged if you need.
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nice to have all implement all Listener, but not necessary.
    }
    


## Authors

* **Denis Nascimento** - *Initial work* - [Sensor Manager Android](https://github.com/fdnascimento/Sensor-Manager-Android)

## License

Open source for all.

