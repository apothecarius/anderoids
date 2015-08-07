package de.apoth.anderoids.graphics;

import java.util.ArrayList;

import de.apoth.anderoids.R;
import de.apoth.anderoids.logic.ShipTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * intended to load, store and deliver bitmaps
 * @author apoth
 *
 */
public class BitmapManager {

	private final static int numStarPics = 1;

	private boolean isSetup = false;
	private ArrayList<Bitmap> starPics;
	private ArrayList<Bitmap> asteroidPics;
	
	private Bitmap bomberPic;
	private Bitmap fighterPic;
	private Bitmap dronePic;

	private static BitmapManager singleton;
	
	public static void setup(SpaceView myView)
	{
		singleton = new BitmapManager();
		singleton.loadBitmaps(myView);
	}
	public static BitmapManager get()
	{
		if(singleton == null)
			return null;
		if(!singleton.isSetup)
			return null;//no sense in retrieving a bitmapmanager thats not ready
		else
			return singleton;
	}
	
	private BitmapManager()
	{
		this.starPics = new ArrayList<Bitmap>(numStarPics);
	}
	private void loadBitmaps(SpaceView myView)
	{
		isSetup = true;
		starPics.add(BitmapFactory.decodeResource(myView.getResources(), R.drawable.stara));
		this.fighterPic = BitmapFactory.decodeResource(myView.getResources(), R.drawable.fighter);
	}
	
	public Bitmap getStarPic(int i)
	{
		assert(i <= numStarPics);
		return starPics.get(i);
	}
	public static int getNumStarPics(){ return numStarPics;}
	
	public Bitmap getSpaceshipPic(ShipTypes s)
	{
		assert(s == ShipTypes.Fighter);//XXX
		return fighterPic;
		
	}
}
