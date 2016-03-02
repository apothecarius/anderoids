package de.apoth.anderoids.logic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.util.Pair;

import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.events.CollisionEvent;
import de.apoth.anderoids.logic.events.Event;
import de.apoth.anderoids.resource.Time;

public class CollisionSystem extends AbstractSystem{
	
	public CollisionSystem(EntityManager myEM) {
		super(myEM);
		// TODO Auto-generated constructor stub
	}

	public Set<CollisionEvent> checkCollisions() {
		Set<CollisionEvent> collisions = new LinkedHashSet<CollisionEvent>();
		//TODO check if the spaceship or the bullets collide with the asteroids
		//add each collision to the set and return them for the event handler to react upon
		
		//
		return collisions;
	}

	@Override
	public List<Pair<Time, Event>> handleEvent(Event ev) {
		return null;
	}
	
}
