package de.apoth.anderoids.logic.Events;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import android.util.Pair;

public class EventManager {

	private TreeMap<Time, LinkedList<Event>> storedEvents;
	
	//speeds up the process of retrieving events
	private LinkedList<Event> currentEventSet = null;

	private Time currentTime = null;
	
	public EventManager()
	{
		storedEvents = new TreeMap<Time, LinkedList<Event>>();
	}
	/**
	 * for each point in time that events are logged an entry into the hashmap is made
	 * for points in the future one set is sufficient, in general
	 * @param ev
	 * @param time
	 */
	public void storeEvent(Event ev, Time time)
	{
		LinkedList<Event> itsList;
		if(time.equals(this.currentTime ))
		{
			itsList = this.currentEventSet;
		}
		else
		{
			itsList = storedEvents.get(time);
		}
		
		//time not inserted yet
		if(itsList == null)
		{
			itsList = new LinkedList<Event>();
			storedEvents.put(time, itsList);
		}
		itsList.addLast(ev);
	}
	
	/**
	 * only allows to set Time to the future (higher values)
	 * lower values will not affect the object and crash
	 * 
	 * function will also crash if time is set to the future 
	 * but the currentEventSet is not empty
	 * 
	 * increasing time by offset is not implemented to prevent roundingerrors
	 */
	public void increaseTimeTo(Time newTime)
	{
		assert(newTime.t >= this.currentTime.t);
		this.currentTime = newTime;
		if(this.currentEventSet == null)
		{
			Time lowestKey = this.storedEvents.firstKey();
			//update currentEventSet
			if(lowestKey.t <= this.currentTime.t)
			{
				assert(this.currentEventSet.isEmpty());
				this.currentEventSet = this.storedEvents.get(lowestKey);
			}
		}
	}
	
	/**
	 * will return events from the buffer until the key for it is exceeded by this.currentTime
	 * then null will be returned
	 * @return
	 */
	public Event getCurrentEvent()
	{
		assert(currentEventSet == null || !currentEventSet.isEmpty());
		Event retu;
		if(this.currentEventSet != null)
		{
			retu = currentEventSet.getFirst();
			currentEventSet.removeFirst();
			if(currentEventSet.isEmpty())
			{
				currentEventSet = null;
			}
		}
		else if(! this.storedEvents.isEmpty())
		{
			//check if there is a set of events before the set time
			Time leastKey = this.storedEvents.firstKey();
			if(leastKey.t < this.currentTime.t)
			{
				this.currentEventSet = this.storedEvents.get(leastKey);
				assert(this.currentEventSet != null); 
				retu = this.getCurrentEvent();
			}
			else //nothing to do here
			{
				retu = null;
			}
		}
		else
		{
			retu = null;
		}
		
		
		assert(currentEventSet == null || !currentEventSet.isEmpty());		
		return retu;
	}
}
