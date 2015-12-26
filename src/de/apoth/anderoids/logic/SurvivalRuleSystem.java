package de.apoth.anderoids.logic;

import de.apoth.anderoids.logic.entities.EntityManager;

public class SurvivalRuleSystem extends RuleSystem {

	public SurvivalRuleSystem(EntityManager myEM) {
		super(myEM);
	}

	public SurvivalRuleSystem(EntityManager myEntityManager,
			Difficulties activeDifficulty) {
		super(myEntityManager, activeDifficulty);
	}

}
