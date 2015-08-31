package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.RuleSystem;

/**
 * can be used to show a countdown
 * whenever the event is reached, the screen is mostly filled with the given number
 * and a new event with an index one lower is added until zero
 * @author apoth
 *
 */
public class CountdownEvent implements Event {
	private int ctIdx;
	
	public int getCtIdx() {
		return ctIdx;
	}

	//TODO maybe add a style information
	public CountdownEvent(int idx)
	{
		this.ctIdx = idx;
	}
	
	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == RuleSystem.class)
			return true;
		return false;
	}

}
