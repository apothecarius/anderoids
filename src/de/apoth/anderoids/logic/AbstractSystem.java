package de.apoth.anderoids.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.apoth.anderoids.logic.entities.Component;

public abstract class AbstractSystem {
	
	/**
	 * contains the components which each system handles. For each system, 
	 * these components should always have the same type
	 */
	private HashMap<Integer, Component> components;
	
	//TODO generic class einschraenken should be parameterized. ???
	private Class handledComponents;
	protected AbstractSystem(Class hComp)
	{
		//disable the component table, for eg rulesets, that dont store anything specific
		if(hComp != null)
		{
			assert(hComp.getSuperclass() == Component.class);
			this.handledComponents = hComp;
			this.components = new HashMap<Integer, Component>();
		}
	}
	public void addComponent(Integer id, Component comp)
	{
		assert(! components.containsKey(id));
		assert(handledComponents == comp.getClass());
		this.components.put(id,comp);
	}
	public Component getComponent(Integer id)
	{
		Component retu = this.components.get(id);
		if(retu == null)
			return null;
		
		assert(retu.getClass() == this.handledComponents);
		if(! retu.isValid())
		{
			this.components.remove(id);
			return null;
		}
		else
		{
			return retu;
		}
	}
}
