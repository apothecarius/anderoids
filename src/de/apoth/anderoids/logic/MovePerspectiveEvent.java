package de.apoth.anderoids.logic;

import de.apoth.anderoids.logic.events.Event;

public class MovePerspectiveEvent implements Event {

	private Position mment;
	public MovePerspectiveEvent(Position pDiff) {
		this.mment = pDiff;
	}
	public Position getMovement()
	{
		return this.mment;
	}

	@Override
	public boolean concernsSystem(Class systemClass) {
		if(systemClass == GuiStubSystem.class)
			return true;
		return false;
	}

}
