package de.apoth.anderoids.logic;

import java.util.LinkedHashSet;
import java.util.Set;

import de.apoth.anderoids.logic.Events.CollisionEvent;
import de.apoth.anderoids.logic.entities.CollisionComponent;

public class CollisionSystem extends AbstractSystem{
	
	public CollisionSystem()
	{
		super(CollisionComponent.class);
	}

	public Set<CollisionEvent> checkCollisions() {
		Set<CollisionEvent> collisions = new LinkedHashSet<CollisionEvent>();
		//TODO check if the spaceship or the bullets collide with the asteroids
		//add each collision to the set and return them for the event handler to react upon
		
		//
		return collisions;
	}
	
}
