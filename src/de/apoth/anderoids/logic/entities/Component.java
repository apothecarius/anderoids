package de.apoth.anderoids.logic.entities;

public abstract class Component {
	/**
	 * this value tells, if the entity, of which this component is part of should still be used
	 * 
	 */
	
	@Deprecated //not necessary anymore
	private boolean isValid = true;
	public void invalidate()
	{
		isValid = false;
	}
	public boolean isValid()
	{
		return this.isValid;
	}
}
