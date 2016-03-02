package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.GuiStubSystem;
import de.apoth.anderoids.logic.MovementSystem;
import de.apoth.anderoids.resource.Position;

public class MoveObjectEvent implements Event {

	private Position d;
	private Integer objId;
	
	public MoveObjectEvent(Integer objId, Position distance) {
		this.objId = objId;
		this.d = distance;

	}

	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == MovementSystem.class)
			return true;
		if(systemClass == GuiStubSystem.class)
			return true;
		return false;
	}

	public Position getDistance() {
		return d;
	}

	public Integer getObjectId() {
		return objId;
	}
}
