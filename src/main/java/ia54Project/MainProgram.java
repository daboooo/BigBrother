package ia54Project;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.network.jxse.agent.JxtaJxseKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {
		
		// The following line permits to create a JXTA standard-edition kernel.
		Kernels.setPreferredKernelFactory(new JxtaJxseKernelAgentFactory());
		Kernel kernel = Kernels.get(true);	
		NetAgent agentReceiver = new NetAgent("agentReceiver");
		NetAgent bob = new NetAgent("bob");
		NetAgent tod = new NetAgent("tod");
		kernel.launchHeavyAgent(agentReceiver,"agentReceiver","collecteur");
		kernel.launchHeavyAgent(bob,"bob","receiver");
		//kernel.launchHeavyAgent(tod,"tod","receiver");

	}
}
