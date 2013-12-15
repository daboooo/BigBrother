package org.ia54Project;

import org.ia54Project.agent.MyAgent;
import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.network.jxse.agent.MonitoredKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {
		Kernels.setPreferredKernelFactory(new MonitoredKernelAgentFactory());
		Kernel kernel = Kernels.get(true);	
		
		Agent monAgent = new MyAgent();
		kernel.launchLightAgent(monAgent, "MonAgent");
	}
}
