package de.apoth.anderoids.logic;

public class Time {
	public float t;
	public Time(float t)
	{
		this.t = t;
	}
	
	/**
	 * generates a new time that takes the old time and adds some more
	 * @param current
	 * @param add
	 */
	public Time(Time current, Time add)
	{
		this.t = current.t+add.t;
	}
	
	/**
	 * @return the timedifference between the two given times as absolute
	 * getDifference(a,b) returns the same as getDifference(b,a)
	 */
	public static Time getDifference(Time a, Time b)
	{
		float d = a.t - b.t;
		if(d < 0.0f)
			return new Time(d*(-1));
		else
			return new Time(d);
		
	}
	
}
