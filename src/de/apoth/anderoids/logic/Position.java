package de.apoth.anderoids.logic;

public class Position {
	float x;
	float y;
	
	public Position(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public Position()
	{
		this.x = 0;
		this.y = 0;
	}
	/**
	 * this can be used as copy constructor
	 * @param p
	 */
	public Position(Position p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	public void add(Position p)
	{
		this.x += p.x;
		this.y += p.y;
	}
	public void subtract(Position p)
	{
		this.x -= p.x;
		this.y -= p.y;
	}
	public float getX()
	{
		return this.x;
	}
	public float getY()
	{
		return this.y;
	}
	public void addWithFactor(Position p, float f) {
		this.x += p.x*f;
		this.y += p.y*f;
		
	}
	@Override
	public String toString()
	{
		String retu = String.valueOf(this.x);
		retu += ":";
		retu += String.valueOf(this.y);
		return retu;
	}

}
