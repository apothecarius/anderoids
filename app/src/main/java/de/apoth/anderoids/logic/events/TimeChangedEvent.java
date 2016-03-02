package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.HuntRuleSystem;
import de.apoth.anderoids.logic.RuleSystem;
import de.apoth.anderoids.logic.SurvivalRuleSystem;
import de.apoth.anderoids.resource.Time;


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
		if(systemClass == RuleSystem.class ||
				systemClass == SurvivalRuleSystem.class ||
				systemClass == HuntRuleSystem.class) //all new gamemodes must be added here
			return true;
		return false;
	}

}
