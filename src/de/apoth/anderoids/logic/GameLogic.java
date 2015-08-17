package de.apoth.anderoids.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

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
	private MovementSystem myMovementSystem;
	private CollisionSystem myCollisionSystem;
	
	
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
		this.numTicks = 0;
		//changes = new LinkedList<ChangeMessage>();
	}
	
	
	
	protected synchronized void step()
	{
		numTicks++;

		//1. get touch and rotation input
		Position shipMovement = this.getSpaceshipMovement();
		
		//2. move objects
		
		//spaceship
		this.myMovementSystem.moveObject(this.playersSpaceShipID,shipMovement);
		
		// and other objects
		
		// asteroids move implicitely
		
		//3. detect collisions
		this.myCollisionSystem.checkCollisions();
		
		//4. handle events
		
	}
	
	private Position getSpaceshipMovement() {
		// TODO check device angle
		return new Position();
	}

	/*	private int getNumTicks()
	{
		int retu = this.numTicks;
		this.numTicks = 0;
		return retu;
	}*/
	public static int getElapsedTime()
	{
		return obj.numTicks;
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
	public synchronized LinkedList<GuiUpdateEvent> getVisibleChanges(int spaceShipID) {
		// TODO Auto-generated method stub
		LinkedList<GuiUpdateEvent> retu = new LinkedList<GuiUpdateEvent>();
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
