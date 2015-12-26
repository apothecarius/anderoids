package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.RuleSystem;

/**
 * this event is only pushed out on the start of the game
 * It should start a timeout 
 * @author apoth
 *
 */
public class GameStartedEvent implements Event{

	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == RuleSystem.class)
			return true;
		return false;
	}

}
