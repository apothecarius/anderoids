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
	/*
	@Deprecated
	private MovementSystem hisMovementSystem = null;
	@Deprecated
	private CollisionSystem hisCollisionSystem = null;*/
	
/*	public static EntityManager get()
	{
		if(obj == null)
			obj = new EntityManager();
		return obj;
	}*/
	
	/**
	 * @Deprecated the entitymanager should not know about the other systems.
	 * Instead give the systems a reference to this entitymanager, from which they will then retrieve components from 
	 * @param mo
	 * @param co
	 */
	
	public EntityManager(MovementSystem mo, CollisionSystem co)
	{
		entities = new HashMap<Integer, Entity>();
		freeKeys = new LinkedList<Integer>();
/*		hisCollisionSystem = co;
		hisMovementSystem = mo;
*/	}
	public EntityManager()
	{
		entities = new HashMap<Integer, Entity>();
		freeKeys = new LinkedList<Integer>();
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
		//TODO optimize, add a member referencing the last requested Entity, because the same one could be used by multiple systems
		Entity retu = this.entities.get(id);
		assert(retu != null);
		return retu;
	}
	public Component getComponent(Integer id, Class comp)
	{
		//TODO optimize
		Entity e = this.getEntity(id);
		e.containsKey(comp);
		return e.get(comp);
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
		
		return key;
	}
}
