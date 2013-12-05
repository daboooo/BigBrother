package ia54Project;

import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.ChannelManager;
import org.janusproject.kernel.agent.KernelContext;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.Organization;
import org.janusproject.kernel.network.jxse.agent.JxtaJxseKernelAgentFactory;


public class MainProgram {
	public static void main(String[] args) {
		
		// The following line permits to create a JXTA standard-edition kernel.
		Kernels.setPreferredKernelFactory(new JxtaJxseKernelAgentFactory());
		Kernel kernel = Kernels.get(true);
		
//		HelloWorldAgent agent = new HelloWorldAgent();
//		kernel.launchHeavyAgent(agent,"agent1");
		
		NetAgent agentReceiver = new NetAgent();
		AgentSender agentSender = new AgentSender();
		kernel.launchHeavyAgent(agentReceiver,"agentReceiver");
		//kernel.launchHeavyAgent(agentSender,"agentSender");
		
		//------------------------------------------------------------------------------------------------
		// Get the current Janus kernel.
//	    Kernel janusKernel = Kernels.get();
//	    
//	    // Create the agents and get the address of the agent to interact with.
//	    AgentAddress theAgent = new MyAgent().getAddress();
//	 
//	 
//	    // Get the channel manager of the kernel.
//	    ChannelManager channelManager = janusKernel.getChannelManager();
//	 
//	    // Ask for a channel for interacting with the agent.
//	    // The first parameter is the agent to interact with
//	    // The second parameter is the type of the channel to use.
//	    AgentStateChannel channel = channelManager.getChannel(theAgent, AgentStateChannel.class);
//	 
//	    // Check if the agent accept to interact
//	    if (channel!=null) {
//	      // Display the agent's state
//	      System.out.println("First attribute is "+
//	           channel.getFirstAttribute());
//	      System.out.println("Second attribute is "+
//	           channel.getSecondAttribute());
//	    }
//	    else {
//	      System.err.println("The agent does not accept to interact");
//	      System.err.println("");
//	    }
	}
}
