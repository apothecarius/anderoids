package de.apoth.anderoids.logic.entities;

import java.util.HashMap;

public class Entity extends HashMap<Class,Component>{
	public void addComponent(Component comp)
	{
		assert(! this.containsKey(comp.getClass()));
		this.put(comp.getClass(), comp);
	}
	
	/**
	 * @Deprecated not necessary anymore
	 * "destroys" the object, by telling the systems to ignore/forget it from this point on
	 */
	public void invalidate()
	{
		for(Component comp : this.values())
		{
			comp.invalidate();
		}
	}
}
