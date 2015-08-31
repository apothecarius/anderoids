package de.apoth.anderoids.gui;

/**
 * This Activity runs when you're actually playing the game
 * It sets up the graphics (a surfaceview), the gamelogic,
 * and that the two run independently, so that fancy graphics 
 * dont slow down the game 
 */

import java.util.Timer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import de.apoth.anderoids.graphics.SpaceView;
import de.apoth.anderoids.logic.Difficulties;
import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.GameModes;
import de.apoth.anderoids.logic.GameOptionNames;
import de.apoth.anderoids.logic.LogicTimerTask;
import de.apoth.anderoids.logic.ShipTypes;

public class SpaceActivity extends Activity implements SensorEventListener{

	SpaceView mySpace;
	GameLogic logic;
	Timer logicTimer;
	LogicTimerTask logicTask;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
/*
 * the activity doesnt need to remember this, but SpaceView and GameLogic should	
	boolean gameMode; //true if survival, false if highscorerace
	int difficulty; // on a range from 0..2
	SpaceShip myShip;
*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//read gameoptions from intent
		int temp =  this.getIntent().getIntExtra(GameOptionNames.gameMode, 1337);
		assert(temp != 1337);
		GameModes gameMode = GameModes.values()[temp];
		
		temp = this.getIntent().getIntExtra(GameOptionNames.shipType, 1337);
		assert(temp != 1337);
		ShipTypes shipType = ShipTypes.values()[temp];
		
		temp = this.getIntent().getIntExtra(GameOptionNames.difficulty, 1337);
		assert(temp != 1337);
		assert(temp < Difficulties.values().length);
		
		Difficulties difficulty = Difficulties.values()[temp];
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		//setup graphics
		mySpace = new SpaceView(this,shipType,gameMode);
		setContentView(mySpace);
		
		//setup logic
		GameLogic.setup(gameMode, shipType, difficulty);
		logicTimer = new Timer();
		logicTask = new LogicTimerTask();
		//note that the GameLogic must be setup before scheduling the LogicTimertask
		//since it will call the GameLogic
		logicTimer.scheduleAtFixedRate(logicTask, 0, GameLogic.tickIntervallMsec);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	private float rotationX;
	private float rotationY;
	private float rotationZ;
	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			rotationX = event.values[0];
			rotationY = event.values[1];
			rotationZ = event.values[2];
		}
	}
	public float[] getRotation()
	{
		float[] retu = {0,0,0};
		synchronized (this) {
			retu[0] = rotationX;
			retu[1] = rotationY;
			retu[2] = rotationZ;			
		}
		return retu;
		
	}
	
	protected void onResume()
	{
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	protected void onPause()
	{
		super.onPause();
		logicTimer.cancel();
		mSensorManager.unregisterListener(this);
		this.mySpace.joinThread();
		this.finish();
	}
}
