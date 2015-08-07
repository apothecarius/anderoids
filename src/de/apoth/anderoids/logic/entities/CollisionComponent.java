package de.apoth.anderoids.logic.entities;

/**
 * neccessary data to recognize collisions
 * note that all 
 * @author apoth
 *
 */
public class CollisionComponent extends Component{

	private float radius;
	private PositionComponent posComp;

	public float getRadius() {
		return radius;
	}
	public CollisionComponent(PositionComponent p, float r)
	{
		this.radius = r;
		this.posComp = p;
	}
	
	public boolean collidesWith(CollisionComponent other)
	{
		//TODO implement
		return false;
	}
}
