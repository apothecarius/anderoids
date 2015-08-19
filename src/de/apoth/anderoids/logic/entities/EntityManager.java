package de.apoth.anderoids.logic.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.apoth.anderoids.logic.CollisionSystem;
import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.MovementSystem;

public class EntityManager {
	
	private static EntityManager obj;
	
	private HashMap<Integer, Entity> entities;
	//contains Keys that are freed, for reusing
	private List<Integer> freeKeys;
	//contains the highest key, ever given, 
	//so in case freeKeys is empty, use largestKey+1 and increment
	private Integer largestKey = 0;
	
	
	private MovementSystem hisMovementSystem = null;
	private CollisionSystem hisCollisionSystem = null;
	
/*	public static EntityManager get()
	{
		if(obj == null)
			obj = new EntityManager();
		return obj;
	}*/
	public EntityManager(MovementSystem mo, CollisionSystem co)
	{
		entities = new HashMap<Integer, Entity>();
		freeKeys = new LinkedList<Integer>();
		hisCollisionSystem = co;
		hisMovementSystem = mo;
	}
/*	private boolean _logicSystemsSet = false;
	public void setLogicSystems(MovementSystem mo, CollisionSystem co)
	{
		assert(! _logicSystemsSet); //only call this function exactly once
		
		hisCollisionSystem = co;
		hisMovementSystem = mo;
		
		this._logicSystemsSet = true;
	}*/
	
	public boolean entityHasComponent(Integer id, Class comp)
	{
		//schlaegt vielleicht immer fehl und man muss es anders schreiben
		assert(comp.getSuperclass() == Component.class);

		Entity ent = this.getEntity(id);
		return ent.containsKey(comp);
	}
	
	public Entity getEntity(Integer id)
	{
		Entity retu = this.entities.get(id);
		assert(retu != null);
		return retu;
	}
	public Integer addEntity(Entity ent)
	{
		Integer key;
		if(freeKeys.isEmpty())
		{
			largestKey++;
			key = largestKey;
		}
		else
		{
			key = freeKeys.remove(0);
		}
		assert(! this.entities.containsKey(key));
		this.entities.put(key, ent);
		
		if(ent.containsKey(MovementComponent.class))
		{
			this.hisMovementSystem.addComponent(key, ent.get(MovementComponent.class));
		}
		if(ent.containsKey(CollisionComponent.class))
		{
			this.hisCollisionSystem.addComponent(key, ent.get(CollisionComponent.class));
		}
		//TODO add components as added
		
		
		
		return key;
	}
}
