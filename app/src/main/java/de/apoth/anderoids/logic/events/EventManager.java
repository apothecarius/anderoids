package de.apoth.anderoids.logic.events;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import android.util.Pair;
import de.apoth.anderoids.resource.Time;


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
/*		if(this.currentEventSet == null)
		{
			Time lowestKey = this.storedEvents.firstKey();
			//update currentEventSet
			if(lowestKey.t <= this.currentTime.t)
			{
				assert this.currentEventSet.isEmpty();
				this.currentEventSet = this.storedEvents.get(lowestKey);
			}
		}*/
	}
	
	/**
	 * @return a set of events before this.currentTime
     * or null, if none exists or it is empty
	 */
	private synchronized LinkedList<Event>getCurrentEventSet()
	{
		if(this.currentEventSet != null) {
            return this.currentEventSet;
        }
        if(this.storedEvents.isEmpty())
        {
            return null;
        }
        //else get the oldest eventset before currentTime

        //else collapse all eventsets from storedEvents to a new currentEventSet
        this.currentEventSet = new LinkedList<Event>();
        while(true){
            Time leastKey = this.storedEvents.firstKey();
            if(leastKey.t < this.currentTime.t){
                this.currentEventSet.addAll(storedEvents.get(leastKey));
            }
            else
            {
                break;
            }
        }
        return this.currentEventSet;

	}
	/**
	 * will return events from the buffer until the key for it is exceeded by this.currentTime
	 * then null will be returned
	 * @return
	 */
	public synchronized Event getCurrentEvent()
	{
	//	assert currentEventSet == null || !currentEventSet.isEmpty();
		LinkedList<Event> evl = this.getCurrentEventSet();
		if(evl != null && ! evl.isEmpty())
		{
            Event retu = null;
            try{
                 retu = evl.getFirst();

            }catch (NoSuchElementException e)
            {

                System.out.println("e.getMessage()");
            }
            evl.removeFirst();
			return retu;
		}
		else
			return null;
	}
	public synchronized void addEventNow(Event ev) {
		if(ev == null)
		{
			return;
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
		assert ev == null || this.currentEventSet != null;
	}
	public synchronized void addEvent(Event ev, Time t)
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

	public synchronized int getNumEvents()
	{
		int retu = 0;
		//private TreeMap<Time, LinkedList<Event>> storedEvents;
		for( List<Event> lev : this.storedEvents.values())
		{
			retu += lev.size();
		}
		return retu;
	}
	
}
