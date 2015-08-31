package de.apoth.anderoids.logic;

import java.util.LinkedList;

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
	//private long passedTime;
	private int numTicks = 0;
	private static GameLogic obj;
	private Integer playersSpaceShipID;
	private MovementSystem myMovementSystem;
	private CollisionSystem myCollisionSystem;
	private RuleSystem myRuleSystem;
	private GuiStubSystem myGuiStub;
	private EntityManager myEntityManager;
	private EventManager myEventManager;
	private float[] currentDeviceAngle = null;
	private Time currentTime;
	private static GameModes activeGameMode;
	private static boolean _isSetup = false;
	
	public static void setup(GameModes gameMode, ShipTypes shipType, Difficulties difficulty)
	{
		obj = GameLogic.get();
		activeGameMode = gameMode;
//		RuleSystem.setup(gameMode);
		Entity spaceShip = EntityCreator.makeSpaceShip(shipType);
		
		obj.playersSpaceShipID = obj.myEntityManager.addEntity(spaceShip);
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
//		this.numTicks = 0;
		this.currentTime = new Time(0.0f);
		this.currentDeviceAngle = new float[3];
		
		this.myCollisionSystem = new CollisionSystem();
		this.myMovementSystem = new MovementSystem();
		this.myEntityManager = new EntityManager(myMovementSystem,myCollisionSystem);
		this.myEventManager = new EventManager();
		this.myGuiStub = new GuiStubSystem();
		if(activeGameMode == GameModes.Hunt)
			this.myRuleSystem = new HuntRuleSystem();
		else
			this.myRuleSystem = new SurvivalRuleSystem();
	}
	
	
	
	public synchronized void executeEventsUntilNow()
	{
		Event ev = this.myEventManager.getCurrentEvent();
		if(ev == null)
			return;
		if(ev.concernsSystem(MovementSystem.class))
			this.myMovementSystem.handleEvent(ev);
		if(ev.concernsSystem(CollisionSystem.class))
			this.myCollisionSystem.handleEvent(ev);
		return;
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
