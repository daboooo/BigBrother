package org.ia54Project;

import java.net.InetAddress;

import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.organization.OrganizationController;
import org.ia54Project.organization.RoleGUIManager;
import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.GroupAddress;

public class BigBrotherUtil {
	public static BigBrotherChannel getChannelFor(AgentAddress ag) {
		Kernel kernel = Kernels.get();
		if (kernel!=null) {
			BigBrotherChannel channel = kernel.getChannelManager().getChannel(ag,BigBrotherChannel.class);
			if (channel!=null) return channel;
		}
		throw new IllegalStateException("no BigBrother channel"); //$NON-NLS-1$
	}
	
	public static BigBrotherChannel getChannelForGUI(AgentAddress guiAgentAddress, GroupAddress gadr) {
		
		Kernel kernel = Kernels.get();
		if (kernel!=null) {
//			kernel.getExistingGroup(OrganizationController.class);
//			kernel.
			BigBrotherChannel channel = kernel.getChannelManager().getChannel(guiAgentAddress,gadr,RoleGUIManager.class,BigBrotherChannel.class);
			if (channel!=null) {
				return channel; 
			} else {
				return getChannelForGUI(guiAgentAddress, gadr);
			}

		}
		throw new IllegalStateException("no BigBrother channel for role GUIMANAGER"); //$NON-NLS-1$
	}
	
	public static String getComputerFullName() {
	    String hostName = null;
	    try {
	      final InetAddress addr = InetAddress.getLocalHost();
	      hostName = new String(addr.getHostName());
	    } catch(final Exception e) {
	    }//end try
	    return hostName;
	  }//end getComputerFullName
	
	public static String getIP() {
	    String addr = null;
	    try {
	      addr = InetAddress.getLocalHost().getHostAddress();
	    } catch(final Exception e) {
	    }//end try
	    return addr;
	  }//
	
	public static void printMachineModel(MachineModel mm) {
		System.out.println("------------------------- DEBUG MM ------------------");
		System.out.println(mm.getName());
		System.out.println(mm.getIp());
		System.out.println(mm.getKernelList());
		System.out.println("------------------------- DEBUG MM ------------------");
	}
}
