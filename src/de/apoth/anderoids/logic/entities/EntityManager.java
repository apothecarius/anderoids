package de.apoth.anderoids.logic.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EntityManager {
	
	private static EntityManager obj;
	
	private HashMap<Integer, Entity> entities;
	//contains Keys that are freed, for reusing
	private List<Integer> freeKeys;
	//contains the highest key, ever given, 
	//so in case freeKeys is empty, use largestKey+1 and increment
	private Integer largestKey = 0;
	
	public static EntityManager get()
	{
		if(obj == null)
			obj = new EntityManager();
		return obj;
	}
	private EntityManager()
	{
		entities = new HashMap<Integer, Entity>();
		freeKeys = new LinkedList<Integer>();
	}
	
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
		return key;
	}
}
