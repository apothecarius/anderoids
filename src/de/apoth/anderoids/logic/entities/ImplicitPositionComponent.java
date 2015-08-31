package de.apoth.anderoids.logic.entities;

import de.apoth.anderoids.logic.GameLogic;
import de.apoth.anderoids.logic.Position;
import de.apoth.anderoids.logic.Time;
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
	private Time creationTime;
	public void setInertia(Position p)
	{
		this.inertia = p;
	}
	public ImplicitPositionComponent(Position p) {
		super(p);
		this.inertia = new Position();
		this.creationTime = GameLogic.get().getCurrentTime();
	}
	
	@Override
	public Position getPosition()
	{
		Time time = GameLogic.get().getCurrentTime();
		time = Time.getDifference(time, this.creationTime);
		assert(time.t >= 0.0f); //prevent rounding errors
		Position retu = new Position(this.p);
		retu.addWithFactor(this.inertia, time.t);
		return retu;
	}

}
