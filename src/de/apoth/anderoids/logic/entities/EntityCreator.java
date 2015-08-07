package de.apoth.anderoids.logic.entities;

import de.apoth.anderoids.logic.Position;
import de.apoth.anderoids.logic.ShipTypes;

/**
 * This class creates entities. Every definition of objects, e.g. how large a 
 * medium asteroid is stands somewhere here
 * 
 * All functions can be static, because there is no state
 * @author apoth
 *
 */

public class EntityCreator {
	public static Entity makeSpaceShip(ShipTypes t)
	{
		//TODO adjust, also depending on shiptype
		float radius = 13.37f;
		Entity retu = new Entity();
		PositionComponent pc = new PositionComponent(new Position(0,0));
		retu.addComponent(pc);
		retu.addComponent( new CollisionComponent(pc,radius));
		retu.addComponent( new MovementComponent(pc));
		
		//further ideas: energy component for shields, weapons and drive power
		
		return retu;
	}
	
	private EntityCreator()
	{
		
	}
}
