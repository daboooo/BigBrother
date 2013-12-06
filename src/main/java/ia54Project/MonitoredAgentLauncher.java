package ia54Project;


import java.util.Vector;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;

public class MonitoredAgentLauncher {
	static private Vector<Agent> monitoredAgent = new Vector<Agent>();

	static public AgentAddress launchHeavyAgent(Kernel k, Agent agent, Object... initParameters) {
		// saving the Agent
		monitoredAgent.add(agent);
		return k.launchHeavyAgent(agent, initParameters);
	}

	static public Vector<Agent> getAgents() {
		return monitoredAgent;
	}
}
