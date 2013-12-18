package org.ia54Project;
import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.network.jxse.agent.MonitoredKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {

		Kernels.setPreferredKernelFactory(new MonitoredKernelAgentFactory(true));
		Kernel kernel = Kernels.get(true);
		
//		MyAgent myAgent = new MyAgent();
//		kernel.launchHeavyAgent(myAgent, "MY_AGENT");

	}
}
