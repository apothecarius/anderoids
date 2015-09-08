package de.apoth.anderoids.logic;

import java.util.LinkedList;

import android.util.Log;

import de.apoth.anderoids.logic.entities.Entity;
import de.apoth.anderoids.logic.entities.EntityCreator;
import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.events.Event;
import de.apoth.anderoids.logic.events.EventManager;
import de.apoth.anderoids.logic.events.TimeChangedEvent;

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
	private static GameLogic obj;
	private Integer playersSpaceShipID;
	private MovementSystem myMovementSystem;
	private CollisionSystem myCollisionSystem;
	private RuleSystem myRuleSystem;
	private GuiStubSystem myGuiStub;
	//contains all systems for convenience
	private LinkedList<AbstractSystem> _allMySystems;
	private EntityManager myEntityManager;
	private EventManager myEventManager;
	private float[] currentDeviceAngle = null;
	private Time currentTime;
	private static GameModes activeGameMode;
	private static boolean _isSetup = false;
	private static Difficulties activeDifficulty;
	//TODO replace with a list containing the ship details for each player
	private static ShipTypes thisPlayersShiptype;
	
	public static void setup(GameModes gameMode, ShipTypes shipType, Difficulties difficulty)
	{
		activeGameMode = gameMode;
		activeDifficulty = difficulty;
		thisPlayersShiptype = shipType;
		
		_isSetup = true;
	}
	
	public static GameLogic get()
	{
		if(obj == null)
			obj = new GameLogic();
		return obj;
	}
	private GameLogic()
	{
		assert(_isSetup);
		this._allMySystems = new LinkedList<AbstractSystem>();
		this.currentTime = new Time(0.0f);
		this.currentDeviceAngle = new float[3];
		
		this.myEventManager = new EventManager(this.currentTime);
		this.myEntityManager = new EntityManager();
		
		this.myCollisionSystem = new CollisionSystem(myEntityManager);
		_allMySystems.add(myCollisionSystem);
		this.myMovementSystem = new MovementSystem(myEntityManager);
		_allMySystems.add(myMovementSystem);
		
		this.myGuiStub = new GuiStubSystem(myEntityManager);
		_allMySystems.add(myGuiStub);
		if(activeGameMode == GameModes.Hunt)
			this.myRuleSystem = new HuntRuleSystem(myEntityManager);
		else
			this.myRuleSystem = new SurvivalRuleSystem(myEntityManager);
		_allMySystems.add(myRuleSystem);
		
		//TODO supposed to be reaction to an event (maybe?)
		Entity spaceShip = EntityCreator.makeSpaceShip(this.thisPlayersShiptype);
		this.playersSpaceShipID = this.myEntityManager.addEntity(spaceShip);
		//TODO send event to GUI that its supposed to follow this spaceship
	}
	
	
	
	public synchronized void executeEventsUntilNow()
	{
		while(true)
		{
			Event ev = this.myEventManager.getCurrentEvent();
			if(ev == null)
				return;
			for(AbstractSystem sys : this._allMySystems)
			{
				if(ev.concernsSystem(sys.getClass()))
				{
					this.myEventManager.addAllEvents(sys.handleEvent(ev));
				}
			}
		}
	}
	
	public Time getCurrentTime()
	{
		return this.currentTime;
	}
	public void setDeviceAngle(float[] angle)
	{
		assert(angle != null);
		assert(currentDeviceAngle != null);
		assert(angle.length == 3);
		for (int i = 0; i < angle.length; i++) {
			this.currentDeviceAngle[i] = angle[i];
		}
		return;
	}
	public float[] getDeviceAngle()
	{
		return this.currentDeviceAngle;
	}

	
	/**
	 * returns all changes to objects that are visible to the player with the ship of the given ID
	 * 
	 * @param spaceShipID
	 * @return
	 */
	public synchronized LinkedList<Event> getVisibleChanges(int spaceShipID) {
		return this.myGuiStub.popAllGuiEvents();
	}

	public Integer getMySpaceShipID() {
		return this.playersSpaceShipID;
	}

	
	public Time getTime()
	{
		return this.currentTime;
	}
	public void elapseTime(Time t) {
		assert(this.currentTime != null);
		Time newTime = new Time(this.currentTime,t);
		Event timeChange = new TimeChangedEvent(this.currentTime, newTime);
		this.currentTime = newTime;
		this.myEventManager.addEventNow(timeChange);
		this.myEventManager.increaseTimeTo(this.currentTime);
		
	}
	public void elapseTime()
	{
		this.elapseTime(new Time(0.02f));
	}
	
	
	
	

}
