package de.apoth.anderoids.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.Pair;
import de.apoth.anderoids.logic.entities.Component;
import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.events.Event;

@SuppressLint("Assert")
public abstract class AbstractSystem {
	
	/**
	 * @Deprecated
	 * contains the components which each system handles. For each system, 
	 * these components should always have the same type
	 */
	private HashMap<Integer, Component> components;
	protected EntityManager myEntityManager;
	
	// handled componentsystem ist dummscheiss, die systeme sollen eine referenz zum entitymanager haben und die holen
	private Class handledComponents;
/*	@Deprecated
	protected AbstractSystem(Class hComp)
	{
		//disable the component table, for eg rulesets, that dont store anything specific
		if(hComp != null)
		{
			assert(hComp.getSuperclass() == Component.class);
			this.handledComponents = hComp;
			this.components = new HashMap<Integer, Component>();
		}
	}*/
	public AbstractSystem(EntityManager myEM)
	{
		this.myEntityManager = myEM;
	}

	public abstract List<Pair<Time,Event>> handleEvent(Event ev);
	
}
