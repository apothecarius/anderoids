package de.apoth.anderoids.logic;

import java.util.List;

import android.util.Pair;
import de.apoth.anderoids.logic.events.Event;

/**
 * 
 * this system stores and possibly combines events for the GUI
 * @author apoth
 *
 */
public class GuiStubSystem extends AbstractSystem {

	protected GuiStubSystem() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Pair<Time, Event>> handleEvent(Event ev) {
		// TODO Auto-generated method stub
		return null;
	}

}
