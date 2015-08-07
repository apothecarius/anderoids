package de.apoth.anderoids.logic;

import java.util.TimerTask;

public class LogicTimerTask extends TimerTask {
	public void run()
	{
		GameLogic.get().step();
	}

}
