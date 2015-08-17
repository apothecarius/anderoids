package de.apoth.anderoids.logic;

import de.apoth.anderoids.logic.entities.MovementComponent;

public class MovementSystem extends AbstractSystem {

	public MovementSystem() 
	{
		super(MovementComponent.class);
	}

	public void moveObject(Integer objectId, Position mment) {
		MovementComponent pc = (MovementComponent) this.getComponent(objectId);
		
		if(pc == null) //object not found for whatever reason
			return;
		else
			pc.move(mment);
		return;
		
	}

}
