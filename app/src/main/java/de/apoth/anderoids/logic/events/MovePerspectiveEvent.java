package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.GuiStubSystem;
import de.apoth.anderoids.resource.Position;

//@Deprecated
//wieso eigenltich
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

    public Position getDistance()
    {
        return this.mment;
    }
    @Override
    public String toString()
    {
        return "PerspMov by: " + Float.toString(mment.getX()) + ":"+Float.toString(mment.getY());
    }

}
