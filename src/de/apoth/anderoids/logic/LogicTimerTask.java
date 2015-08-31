package de.apoth.anderoids.logic;

import java.util.TimerTask;

public class LogicTimerTask extends TimerTask 
{
	public void run()
	{
		//TODO synchronize this function in a way that prevents a stack of calls heaping up
		// check time and call elapseTime(t) instead, return immediately if function is running already
		GameLogic.get().elapseTime();
		GameLogic.get().executeEventsUntilNow();
	}

}
