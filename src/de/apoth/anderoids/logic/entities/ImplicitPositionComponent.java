package de.apoth.anderoids.logic.entities;

import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.Position;
/**
 * This component allows for linear movement to be calculated on demand, 
 * which reduces the amount of objects moved in each logic tick. 
 * 
 * One would think that the class to derive from is actually MovementComponent, 
 * but since the getPosition Function must be changed its not so
 * @author apoth
 *
 */
public class ImplicitPositionComponent extends PositionComponent {

	private Position inertia = null;
	private float creationTime;
	public void setInertia(Position p)
	{
		this.inertia = p;
	}
	public ImplicitPositionComponent(Position p) {
		super(p);
		this.inertia = new Position();
		this.creationTime = GameLogic.getElapsedTime();
	}
	
	@Override
	public Position getPosition()
	{
		float time = GameLogic.getElapsedTime();
		time -= this.creationTime;
		assert(time >= -0.0001f); //prevent rounding errors
		Position retu = new Position(this.p);
		retu.addWithFactor(inertia, time);
		return retu;
	}

}
