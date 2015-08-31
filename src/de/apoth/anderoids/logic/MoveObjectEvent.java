package de.apoth.anderoids.logic;

import de.apoth.anderoids.logic.events.Event;

public class MoveObjectEvent implements Event {

	Position d;
	Integer objId;
	
	public MoveObjectEvent(Integer objId, Position distance) {
		this.objId = objId;
		this.d = distance;

	}

	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == MovementSystem.class)
			return true;
		return false;
	}

}
