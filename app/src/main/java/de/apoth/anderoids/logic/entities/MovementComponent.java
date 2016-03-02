package de.apoth.anderoids.logic.entities;

import de.apoth.anderoids.resource.Position;

public class MovementComponent extends Component {
	private PositionComponent pc;
	
	public MovementComponent(PositionComponent pc) {
		this.pc = pc;
	}
	public void move(Position p)
	{
		this.pc.p.add(p);
	}
	
}
