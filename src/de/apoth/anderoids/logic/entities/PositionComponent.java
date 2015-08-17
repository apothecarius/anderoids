package de.apoth.anderoids.logic.entities;

import de.apoth.anderoids.logic.Position;

public class PositionComponent extends Component {
	protected Position p;
	public PositionComponent(Position p) {
		this.p = p;
	}
	
	public Position getPosition()
	{
		return new Position(this.p);
	}
}
