package de.apoth.anderoids.logic;

import java.util.LinkedList;
import java.util.List;

import android.util.Pair;
import de.apoth.anderoids.logic.entities.EntityManager;
import de.apoth.anderoids.logic.events.Event;
import de.apoth.anderoids.logic.events.MoveObjectEvent;
import de.apoth.anderoids.logic.events.MovePerspectiveEvent;
import de.apoth.anderoids.logic.events.TimeChangedEvent;
import de.apoth.anderoids.resource.Position;
import de.apoth.anderoids.resource.Time;

public class RuleSystem extends AbstractSystem {

	protected Difficulties difficultySetting;

	public RuleSystem(EntityManager myEM) {
		super(myEM);
	}
	public RuleSystem(EntityManager myEM,Difficulties diff)
	{
		super(myEM);
		this.difficultySetting = diff;
	}

	@Override
	public List<Pair<Time, Event>> handleEvent(Event ev)
	{
		List<Pair<Time, Event>> retu = new LinkedList<Pair<Time,Event>>();
		
		if(ev.getClass() == TimeChangedEvent.class)
		{
			TimeChangedEvent tev = (TimeChangedEvent) ev;
			Time time = Time.getDifference(tev.getNewTime(), tev.getOldTime());
			float tDiff = time.t;
			
			float[] angle = GameLogic.get().getDeviceAngle();
			Position pDiff = new Position(-angle[1]*tDiff*50.0f, -angle[0]*tDiff*50.0f);
			Event moveSpaceShip = new MoveObjectEvent(GameLogic.get().getMySpaceShipID(),pDiff);
			retu.add(new Pair<Time, Event>(null, moveSpaceShip));
			
			Event movePerspectiveAfterSpaceship = new MovePerspectiveEvent(pDiff);
			retu.add(new Pair<Time, Event>(null, movePerspectiveAfterSpaceship));

		}

		return retu;
	}

}
