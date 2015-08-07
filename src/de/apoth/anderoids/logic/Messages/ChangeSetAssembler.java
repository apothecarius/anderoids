package de.apoth.anderoids.logic.Messages;

import java.util.LinkedList;

import de.apoth.anderoids.logic.GameLogic;

/**
 * The connection class between logic and GUI. 
 * 
 * This class is supposed to collect all changes of objects (new, deleted, moved, rotated)
 * ??? necessary ???
 * @author apoth
 *
 */
public class ChangeSetAssembler
{
	public static LinkedList<ChangeMessage> getChanges(Integer spaceShipID)
	{
		GameLogic gl = GameLogic.get();
		LinkedList<ChangeMessage> retu = new LinkedList<ChangeMessage>();
		
		//assert(gl != null);
		//or rather
		if(gl == null)
			return retu;
		
		retu.addAll(gl.getVisibleChanges(spaceShipID));
		return retu;
	}
}
