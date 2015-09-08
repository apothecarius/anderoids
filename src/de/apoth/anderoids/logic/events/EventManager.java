package de.apoth.anderoids.logic.events;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import android.util.Pair;

import de.apoth.anderoids.logic.Time;

public class EventManager {

	private TreeMap<Time, LinkedList<Event>> storedEvents;
	
	//speeds up the process of retrieving events
	private LinkedList<Event> currentEventSet = null;

	private Time currentTime = null;
	
	public EventManager(Time now)
	{
		storedEvents = new TreeMap<Time, LinkedList<Event>>();
		this.currentTime = now;
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
		assert newTime.t >= this.currentTime.t;
		this.currentTime = newTime;
		if(this.currentEventSet == null)
		{
			Time lowestKey = this.storedEvents.firstKey();
			//update currentEventSet
			if(lowestKey.t <= this.currentTime.t)
			{
				assert this.currentEventSet.isEmpty();
				this.currentEventSet = this.storedEvents.get(lowestKey);
			}
		}
	}
	
	
	private LinkedList<Event>getCurrentEventSet()
	{
		if(this.currentEventSet != null && this.currentEventSet.isEmpty())
			this.currentEventSet = null;
		if(this.currentEventSet == null)
		{
			//check if there is a set of events before the set time
			boolean searchDone = false;
			do
			{
				if(this.storedEvents.isEmpty())
				{
					return null;
				}
				Time leastKey = this.storedEvents.firstKey();
				if(leastKey.t < this.currentTime.t)
				{
					this.currentEventSet = this.storedEvents.get(leastKey);
					assert this.currentEventSet != null;
					if(this.currentEventSet.isEmpty())
					{
						storedEvents.remove(leastKey);
						this.currentEventSet = null;
					}
					else //found nonempty list
					{
						searchDone = true;
					}
				}
				else //next events lie in the future
				{
					//leave at null and do nothing
					searchDone = true;
				}
			}while(! searchDone);
		}
		//can be null
		return this.currentEventSet;
	}
	/**
	 * will return events from the buffer until the key for it is exceeded by this.currentTime
	 * then null will be returned
	 * @return
	 */
	public Event getCurrentEvent()
	{
	//	assert currentEventSet == null || !currentEventSet.isEmpty();
		LinkedList<Event> evl = this.getCurrentEventSet();
		if(evl != null)
		{
			assert ! evl.isEmpty();
			Event retu = evl.getFirst();
			evl.removeFirst();
			return retu;
		}
		else 
			return null;
					
		/*if(this.currentEventSet != null)
		{
			assert ! currentEventSet.isEmpty();
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
				assert this.currentEventSet != null; 
				assert ! this.currentEventSet.isEmpty();
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
		assert this.currentEventSet == null || ! this.currentEventSet.isEmpty();

		
		assert currentEventSet == null || !currentEventSet.isEmpty();*/		
	}
	public void addEventNow(Event ev) {
		if(ev == null)
		{
			
		}
		else if(this.currentEventSet != null)
		{
			this.currentEventSet.add(ev);
		}
		else
		{
			LinkedList<Event> newList = new LinkedList<Event>();
			newList.add(ev);
			this.currentEventSet = newList;
			this.storedEvents.put(this.currentTime, newList);
		}
		assert ev == null || this.currentEventSet != null ;
	}
	public void addEvent(Event ev, Time t)
	{
		if(ev == null)
			return;
		if(t== null || t.equals(this.currentTime))
		{
			this.addEventNow(ev);
		}
		else
		{
			LinkedList<Event> itsList = this.storedEvents.get(t);
			if(itsList == null)
			{
				assert ! this.storedEvents.containsKey(t);
				itsList = new LinkedList<Event>();
				this.storedEvents.put(t, itsList);
			}
			itsList.add(ev);
			
			itsList = storedEvents.get(t);
		}
	}
	public void addAllEvents(Collection<Pair<Time, Event>> eventSet) {
		if(eventSet == null)
			return;
		for(Pair<Time,Event> pev : eventSet)
		{
			this.addEvent(pev.second, pev.first);
		}
		
	}
	
}
