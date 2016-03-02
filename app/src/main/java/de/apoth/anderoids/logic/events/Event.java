package de.apoth.anderoids.logic.events;

import de.apoth.anderoids.logic.MovementSystem;

public interface Event {

	public abstract boolean concernsSystem(Class systemClass);

}
