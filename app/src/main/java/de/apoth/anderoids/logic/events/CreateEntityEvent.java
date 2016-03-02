package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.RuleSystem;

public class CreateEntityEvent implements Event {

	@Override
	public boolean concernsSystem(Class systemClass) {
		//TODO maybe add a representative for creating stuff in the entitymanager
		if(systemClass == RuleSystem.class)
			return true;
		return false;
	}

}
