package org.janusproject.kernel.network.jxse.agent;


import java.util.EventListener;

import org.janusproject.kernel.agent.AgentActivator;
import org.janusproject.kernel.agent.KernelAgent;
import org.janusproject.kernel.agent.KernelAgentFactory;
import org.janusproject.kernel.network.jxse.agent.MonitoredKernelAgent;
import org.janusproject.kernel.network.jxse.jxta.impl.DefaultJxtaNetworkAdapter;

public class MonitoredKernelAgentFactory implements KernelAgentFactory {
	private Boolean gui;
	public MonitoredKernelAgentFactory(Boolean gui) {
		System.out.println("JE SUIS LE KERNELFACTORY");
		this.gui = gui;
	}

	public KernelAgent newInstance(Boolean commitSuicide,
			AgentActivator activator, EventListener startUpListener,
			String applicationName) throws Exception {
		System.out.println("JE SUIS LE KERNELFACTORY-KERNEL");
		
		return new MonitoredKernelAgent(gui, activator, commitSuicide, startUpListener, applicationName, new DefaultJxtaNetworkAdapter());
	}

}

