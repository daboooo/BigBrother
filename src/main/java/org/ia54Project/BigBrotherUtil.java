package org.ia54Project;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.Vector;

import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.ia54Project.organization.RoleGUIManager;
import org.janusproject.kernel.Kernel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Agent;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.organization.Group;

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
			BigBrotherChannel channel = kernel.getChannelManager().getChannel(guiAgentAddress,gadr,RoleGUIManager.class,BigBrotherChannel.class);
			if (channel!=null) {
				return channel; 
			} else {
				return getChannelForGUI(guiAgentAddress, gadr);
			}

		}
		throw new IllegalStateException("no BigBrother channel for role GUIMANAGER"); //$NON-NLS-1$
	}
	
	/**
	 * Method to get the computer's name
	 * @return the computer's name
	 */
	public static String getComputerFullName() {
	    String hostName = null;
	    try {
	      final InetAddress addr = InetAddress.getLocalHost();
	      hostName = new String(addr.getHostName());
	    } catch(final Exception e) {
	    }//end try
	    return hostName;
	  }//end getComputerFullName
	
	/**
	 * Method to get an ip on a local network
	 * @return the ip address
	 */
	public static String getIP() {
	    String addr = null;
	    try {
	      addr = InetAddress.getLocalHost().getHostAddress();
	    } catch(final Exception e) {
	    }//end try
	    return addr;
	  }//
	
	public static void printMachineModel(MachineModel mm) {
		System.out.println("- START DEBUG MM ------------------");
		System.out.println(mm.getName());
		System.out.println(mm.getIp());
		Vector<KernelModel> kernels = (Vector<KernelModel>) mm.getKernelList();
		if(kernels != null) {
			for (KernelModel kernelModel : kernels) {
				printKernelModel(kernelModel);
			}
		}
		System.out.println("-  END DEBUG MM ------------------");
	}

	public static void printKernelModel(KernelModel kernelModel) {
		System.out.println("----- START DEBUG KERNEL MODEL");
		if(kernelModel != null) {
			System.out.println("Name: " + kernelModel.getName());
			System.out.println("Address: " + kernelModel.getKernelAddress());
			System.out.println("Class: " + kernelModel.getClass());
			Vector<AgentModel> lonely = (Vector<AgentModel>) kernelModel.getLonelyAgentList();
			Vector<OrganizationModel> orgs = (Vector<OrganizationModel>) kernelModel.getOrgList();
			System.out.println(" -- START LONELY LIST:");
			if(lonely!= null) {
				for (AgentModel agentModel : lonely) {
					printAgentModel(agentModel);
				}
			} else {
				System.out.println("lonely list null");
			}
			System.out.println(" -- END LONELY LIST:");
			System.out.println(" -- START ORGLIST:");
			if(orgs != null) {
				for (OrganizationModel organizationModel : orgs) {
					printOrganizationModel(organizationModel);
				}
			} else {
				System.out.println("orgs null");
			}
			System.out.println(" -- END ORGLIST:");
			
			
			
		} else {
			System.out.println("KernelModel null");
		}
		System.out.println("----- END DEBUG KERNEL MODEL");
	}

	public static void printOrganizationModel(
			OrganizationModel organizationModel) {
		System.out.println(" -------------------- START organizationmodel:");
		if(organizationModel != null) {
			System.out.println("org:Classe " + organizationModel.getClasse());
			System.out.println("org:Nb Instance " + organizationModel.getNbInstance());
			Vector<GroupModel> groups = (Vector<GroupModel>) organizationModel.getGroupList();
			if(groups != null) {
				System.out.println("org:GroupList >>");
				for (GroupModel groupModel : groups) {
					printGroupModel(groupModel);
				}
				System.out.println("org:GroupList <<");
			} else {
				System.out.println("org:GroupList null");
			}
		} else {
			System.out.println("orgModel null");
		}
		
		System.out.println(" -------------------- END organizationmodel:");
	}

	public static void printGroupModel(GroupModel groupModel) {
		System.out.println(" -------------------- .-.--- START groupmodel:");
		if(groupModel != null) {
			System.out.println("gr: Address: " + groupModel.getGroupAddress());
			Vector<RoleModel> roles = (Vector<RoleModel>) groupModel.getRoleList();
			if(roles != null) {
				System.out.println("gr: Role list >> ");
				for (RoleModel roleModel : roles) {
					printRoleModel(roleModel);
				}
				
				System.out.println("gr: Role list << ");
			} else {
				System.out.println("gr: Role list null ");
			}
		} else {
			System.out.println("gr: groupModel null");
		}
		System.out.println(" -------------------- .-.--- END groupmodel:");
		
	}

	public static void printRoleModel(RoleModel roleModel) {
		System.out.println(" _________________________________ START rolemodel:");
		if(roleModel!=null) {
			System.out.println("rm: Classe" + roleModel.getClasse());
			System.out.println("rm: GroupAddress" + roleModel.getGroupAdress());
			System.out.println("rm: RoleAddress" + roleModel.getRoleAddress());
			Vector<AgentModel> players =  (Vector<AgentModel>) roleModel.getPlayerList();
			if(players != null) {
				System.out.println("rm: playerList >>");
				for (AgentModel agentModel : players) {
					printAgentModel(agentModel);
				}
				System.out.println("rm: playerList <<");
			} else {
				System.out.println("rm: playerList null");
			}
		} else {
			System.out.println("rm: null");
		}
		
		System.out.println(" _________________________________ END rolemodel:");
	}

	public static void printAgentModel(AgentModel agentModel) {
		// TODO Auto-generated method stub
		if(agentModel != null) {
			System.out.println("ag: Name: " + agentModel.getName());
			System.out.println("ag: Address: " + agentModel.getAddress());
			System.out.println("ag: Creator Address: " + agentModel.getCreatorAddress());
			System.out.println("ag: List of role: " + agentModel.getListOfRole());
			System.out.println("supposed to print more: TODO");
		} else {
			System.out.println("ag: null");
		}
	}
	
	
	// TO DEBUG GROUP AGENT AND ROLE FROM KERNEL 
//	Vector<Group> groups= new Vector<Group>();
//	Vector<Agent> agents = new Vector<Agent>();
//	Iterator<GroupAddress> itgr = getGroupRepository().iterator();
//	Iterator<AgentAddress> itagAd = getAgentRepository().iterator();
//	while(itgr.hasNext()) {
//		groups.add(getGroupObject(itgr.next()));
//	}
//	
//	while(itagAd.hasNext()) {
//		agents.add(getAgentRepository().get(itagAd.next()));
//	}
//	
//	System.out.println("FROM kernel: printing group");
//	for (Group group : groups) {
//		System.out.println("Org: " + group.getOrganization());
//		System.out.println("PlayerCount: " + group.getPlayerCount());
//		System.out.println("LISTE DES ROLES: " );
//		
//		for (Agent agent : agents) {
//			System.out.println("Agent " + agent.getName() + " joue " + agent.getRoles(group.getAddress()) + " DANS " + group.getOrganization());
//		}
//
//	}
//	System.out.println("<< kernel: printing group END");
}
