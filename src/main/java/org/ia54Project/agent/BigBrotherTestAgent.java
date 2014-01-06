package org.ia54Project.agent;

import org.ia54Project.dataModel.AgentModel;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.status.Status;

/**
 * A simple agent that can be used for different test
 * @author Arnaud Roblin, Julien Benichou
 *
 */
public class BigBrotherTestAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1757539615857722779L;

	
	@Override
	public Status live() {
		print("I live testing");

		return super.live();
	}
	
}
