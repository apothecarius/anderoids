package de.apoth.anderoids.logic;

import de.apoth.anderoids.logic.entities.EntityManager;

public class HuntRuleSystem extends RuleSystem{

	public HuntRuleSystem(EntityManager myEM) {
		super(myEM);
	}

	public HuntRuleSystem(EntityManager myEntityManager,
			Difficulties activeDifficulty) {
		super(myEntityManager,activeDifficulty);
	}

}
