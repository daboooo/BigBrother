package ia54Project;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.agent.KernelContext;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.network.jxse.agent.JxtaJxseKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {
		
		// The following line permits to create a JXTA standard-edition kernel.
		Kernels.setPreferredKernelFactory(new JxtaJxseKernelAgentFactory());
		Kernel kernel = Kernels.get(true);
		HelloWorldAgent agent = new HelloWorldAgent();
		kernel.launchHeavyAgent(agent,"agent1");
	}
}
