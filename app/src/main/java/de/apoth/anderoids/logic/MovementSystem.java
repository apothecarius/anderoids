package de.apoth.anderoids.logic;

import java.util.LinkedList;
import java.util.List;

import android.util.Pair;
import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.entities.MovementComponent;
import de.apoth.anderoids.logic.events.Event;
import de.apoth.anderoids.logic.events.MoveObjectEvent;
import de.apoth.anderoids.resource.Position;
import de.apoth.anderoids.resource.Time;

public class MovementSystem extends AbstractSystem {

	public MovementSystem(EntityManager myEM) {
		super(myEM);
	}

	private void moveObject(Integer objectId, Position mment) {
		
		MovementComponent pc = (MovementComponent) this.myEntityManager.getComponent(
				objectId, MovementComponent.class);
		
		
		if(pc == null) //object not found for whatever reason
			return;
		else
			pc.move(mment);
		return;
	}

	@Override
	public List<Pair<Time, Event>> handleEvent(Event ev) {
		List<Pair<Time,Event>> retu = new LinkedList<Pair<Time,Event>>();
		if(ev.getClass() == MoveObjectEvent.class)
		{
			MoveObjectEvent moe = (MoveObjectEvent)ev;
			this.moveObject(moe.getObjectId(), moe.getDistance());
		}
		return retu;
	}

}
