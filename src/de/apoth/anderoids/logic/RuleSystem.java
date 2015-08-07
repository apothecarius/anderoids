package de.apoth.anderoids.logic;

public class RuleSystem extends AbstractSystem {

	private static RuleSystem obj;
	protected RuleSystem() {
		super(null);
		// TODO Auto-generated constructor stub
	}
	
	public static void setup(GameModes m)
	{
		if(m ==GameModes.Survival)
		{
			obj = new SurvivalRuleSystem();
		}
		else if(m == GameModes.Hunt)
		{
			obj = new HuntRuleSystem();
		}
	}
	public static RuleSystem get()
	{
		assert(obj != null);
		return obj;
	}

}
