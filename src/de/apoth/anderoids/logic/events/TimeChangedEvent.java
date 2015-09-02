package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.RuleSystem;
import de.apoth.anderoids.logic.Time;


public class TimeChangedEvent implements Event {

	Time oldTime;
	Time newTime;
	
	public TimeChangedEvent(Time currentTime, Time newTime) {
		this.oldTime = currentTime;
		this.newTime = newTime;
	}
	public Time getOldTime(){
		return this.oldTime;
	}
	public Time getNewTime()
	{
		return this.newTime;
	}

	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == RuleSystem.class)
			return true;
		return false;
	}

}
