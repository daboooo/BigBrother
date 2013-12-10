package ia54Project;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.network.jxse.agent.JxtaJxseKernelAgentFactory;
import org.janusproject.kernel.network.jxse.agent.MonitoredKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {
		
		// The following line permits to create a JXTA standard-edition kernel.
		Kernels.setPreferredKernelFactory(new MonitoredKernelAgentFactory());
		Kernel kernel = Kernels.get(true);	
		NetAgent agentReceiver = new NetAgent("agentReceiver");
		kernel.launchHeavyAgent(agentReceiver,"tod","receiver");
	//	kernel.launchHeavyAgent(bob,"tod","collecteur");

	}
}
