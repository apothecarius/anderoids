package de.apoth.anderoids.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import de.apoth.anderoids.logic.Messages.ChangeMessage;
import de.apoth.anderoids.logic.entities.Entity;
import de.apoth.anderoids.logic.entities.EntityCreator;
import de.apoth.anderoids.logic.entities.EntityManager;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * This class is a singleton, so that the implementation can be switched out for network games.
 * 
 * @author apoth
 *
 */
public class GameLogic{

	/**
	 * how long is the time between two logicticks supposed to be in milliseconds
	 */
	public static final int tickIntervallMsec = 50;
	//private long passedTime;
	private int numTicks = 0;
	private static GameLogic obj;
	private Integer playersSpaceShipID;
	
	
	public static void setup(GameModes gameMode, ShipTypes shipType, Difficulties difficulty)
	{
		GameLogic obj = GameLogic.get();
		RuleSystem.setup(gameMode);
		Entity spaceShip = EntityCreator.makeSpaceShip(shipType);
		obj.playersSpaceShipID = EntityManager.get().addEntity(spaceShip);
	}
	
	public static GameLogic get()
	{
		if(obj == null)
			obj = new GameLogic();
		return obj;
	}
	private GameLogic()
	{
		//passedTime = 0;
		//changes = new LinkedList<ChangeMessage>();
	}
	
	
	
	protected synchronized void step()
	{
		//passedTime += tickIntervallMsec;
		
		//changes.add(new TimeChangeMessage(passedTime));
		
		//move all objects

		//check for collisions
		
		//check for objects leaving the boundary of vision
		
		//finally
		numTicks++;
	}
	
	private int getNumTicks()
	{
		int retu = this.numTicks;
		this.numTicks = 0;
		return retu;
	}
	/*private long getPassedTime()
	{
		return this.passedTime;
	}*/
	
	
	/**
	 * returns all changes to objects that are visible to the player with the ship of the given ID
	 * 
	 * @param spaceShipID
	 * @return
	 */
	public synchronized LinkedList<ChangeMessage> getVisibleChanges(int spaceShipID) {
		// TODO Auto-generated method stub
		LinkedList<ChangeMessage> retu = new LinkedList<ChangeMessage>();
		//retu.add(new TimeChangeMessage(this.getPassedTime()));
	//	retu.add(new LogicStepCountMessage(this.getNumTicks()));
		
		
		//fuege alle sichtbarkeitsaenderungen hinzu (um ram zu sparen)
		
		//fuege alle explosionen/objektzerstörungen hinzu
		
		//fuege alle logikbedingten grafikeffekte hinzu (schildaufleuchten zb)
		
		//fuege alle absoluten positionsaenderungen hinzu
		
		//fuege alle aenderungen am schwung von objekten hinzu
				
		return retu;
	}

	public Integer getMySpaceShipID() {
		return this.playersSpaceShipID;
	}
	
	
	
	

}
