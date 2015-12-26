package de.apoth.anderoids.logic;

import java.util.LinkedList;
import java.util.List;

import android.util.Pair;
import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.events.Event;

/**
 * 
 * this system stores and possibly combines events for the GUI
 * It should also make the GUI forget about objects that move out of perspective
 * @author apoth
 *
 */
public class GuiStubSystem extends AbstractSystem {



	LinkedList<Event> allEvents = null;
	public GuiStubSystem(EntityManager myEM) {
		super(myEM);
		this.allEvents = new LinkedList<Event>();
	}
	@Override
	public List<Pair<Time, Event>> handleEvent(Event ev) {
		this.allEvents.add(ev);
		return null;
	}
	
	/**
	 * 
	 * returns all added events and forgets them , 
	 * so that they can only be retrieved once
	 */
	public LinkedList<Event> popAllGuiEvents()
	{
		LinkedList<Event> retu = this.allEvents;
		this.allEvents = new LinkedList<Event>();
		return retu;
	}

}
