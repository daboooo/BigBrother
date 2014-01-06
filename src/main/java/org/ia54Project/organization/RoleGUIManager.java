package org.ia54Project.organization;

import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;

import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageMachineModel;
import org.ia54Project.dataModel.MessageOrder;
import org.ia54Project.dataModel.Order;
import org.ia54Project.dataModel.OrderType;
import org.ia54Project.dataModel.RoleModel;
import org.janusproject.kernel.address.Address;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.channels.ChannelInteractable;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.crio.organization.GroupListener;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;

public class RoleGUIManager extends Role implements ChannelInteractable{

	private final BigBrotherChannelImpl bbChannel = new BigBrotherChannelImpl();
	private DataModel bufferedAppInfo = new DataModel(); // linked to the GUI
	private DataModel processAppInfo = new DataModel(); // used to get all informations

	
	
	private State state = State.SLEEPING;
	private State beforePauseState = state;
	private Timer timer;
	private long waitResponsesTime = 1000; // in ms
	private long startingTime;
	private TimerTask task;
	@Override
	public Status end() {
		task.cancel();
		timer.cancel();
		return super.end();
	}

	@Override
	public Status activate(Object... params) {
		// testing purpose
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				loadMachineInfos();
			}
		};
		timer.schedule(task, 500, 2000);
	
		return super.activate(params);
	}
	

	@Override
	public Status live() {
		switch(state) {
		case SENDING:
			Message requestInfo = new StringMessage("request");
			Message response = null;
			//print("sending request");
			broadcastMessage(RoleControlManager.class, requestInfo);
			processAppInfo = new DataModel(new Vector<MachineModel>());
			startingTime = System.currentTimeMillis();
			//print("startingTime = " + startingTime);
			state = State.WAITING_RESPONSE;
		break;
		case WAITING_RESPONSE:
			if(System.currentTimeMillis() - startingTime < waitResponsesTime) {
				response = getMessage();
				if(response != null) {
					if(response instanceof MessageMachineModel) {
						// we got a response
						MachineModel mmreceived = ((MessageMachineModel) response).getContent();
						// check if we already received from this machine
						for (MachineModel machineModel : processAppInfo.getContent()) {
							if(machineModel.getIp().equals(mmreceived.getIp())) {
								// we already got response from this machine, add the kernel to the collection
								Vector<KernelModel> kList = (Vector<KernelModel>) mmreceived.getKernelList();
								if(!kList.isEmpty()) {
									machineModel.getKernelList().add(kList.firstElement());
								}
								//BigBrotherUtil.printMachineModel(machineModel);
								return null;
							}
						}
						
						// new machine ! We add it to the dataModel
						processAppInfo.getContent().add(mmreceived);
					}
				}
			} else {
				swapGUIBuffers();
				state = State.SLEEPING;
			}
		break;
		default:
			
		}
		return null;
	}
	
	private class BigBrotherChannelImpl implements BigBrotherChannel {

		public void switchPause() {
			if(state == State.PAUSED) {
				// resume
				state = beforePauseState;
			} else {
				beforePauseState = state;
				state = State.PAUSED;
			}
			
		}

		public Address getChannelOwner() {
			return getAddress();
		}

		public synchronized DataModel getMachineInfos() {
			return bufferedAppInfo;
		}

		public void addBigBrotherListener(BigBrotherListener listener) {
			bufferedAppInfo.addEventListener(listener);
			
		}

		public synchronized DataModel getData() {
			// TODO Auto-generated method stub
			return getBufferedAppInfo() ;
		}
		
		public void buildAndSendKill(AgentAddress agent) {
			Order order = new Order(agent,OrderType.KILL);
			sendMessage(RoleControlManager.class, new MessageOrder(order));
		}

		public void buildAndSendKill(RoleAddress roleAddressToKill) {
			Vector<AgentAddress> agentsToKill = buildAgentsList(roleAddressToKill);
			if(agentsToKill != null) {
				Order order = new Order(agentsToKill, OrderType.KILL);
				sendMessage(RoleControlManager.class, new MessageOrder(order));	
			}
			
		}
		
		public void buildAndSendKill(GroupAddress groupToKill) {
			Vector<AgentAddress> agentsToKill = buildAgentsList(groupToKill);
			if(agentsToKill != null) {
				Order order = new Order(agentsToKill, OrderType.KILL);
				sendMessage(RoleControlManager.class, new MessageOrder(order));	
			}
		}


		
	}
	
	private Vector<AgentAddress> buildAgentsList(GroupAddress groupAddress) {
		Vector<GroupModel> allGroups = (Vector<GroupModel>) getBufferedAppInfo().getAllGroups();
		Vector<AgentAddress> agentAddresses = null;
		GroupModel groupToReach = null;
		if(groupAddress != null) {
			for (GroupModel groupModel : allGroups) {
				if(groupModel.getGroupAddress() == groupAddress) {
					groupToReach = groupModel;
					break;
				}
			}
			
			if(groupToReach != null) {
				Vector<RoleModel> rolesInGroup = (Vector<RoleModel>) groupToReach.getRoleList();
				if(rolesInGroup != null) {
					agentAddresses = new Vector<AgentAddress>();
					for (RoleModel roleModel : rolesInGroup) {
						Vector<AgentAddress> agentAddressesFromRole = buildAgentsList(roleModel.getRoleAddress());
						if(agentAddressesFromRole != null)
							agentAddresses.addAll(agentAddressesFromRole);
					}
				}
			}
		}
		return agentAddresses;
	}
	
	// return all agents who are related to the role defined by roleAddress
	private Vector<AgentAddress> buildAgentsList(RoleAddress roleAddress) {
		Vector<RoleModel> allRoles = (Vector<RoleModel>) getBufferedAppInfo().getAllRoles();
		Vector<AgentAddress> agentAddresses = null;
		RoleModel roleToKill = null;
		if(allRoles != null) {
			for (RoleModel roleModel : allRoles) {
				if(roleModel.getRoleAddress() == roleAddress) {
					roleToKill = roleModel;
					break;
				}
			}
			Vector<AgentModel> agentToKill = (Vector<AgentModel>) roleToKill.getPlayerList();
			if(agentToKill != null) {
				agentAddresses = new Vector<AgentAddress>();
				for (AgentModel agentModel : agentToKill) {
					agentAddresses.add(agentModel.getAddress());
				}
			}
		}
		return agentAddresses;
		
	}
	
	
	// swap the buffers and notify the UI
	public synchronized void swapGUIBuffers() {
			//System.out.println("swap");
			bufferedAppInfo.setContent(processAppInfo.getContent());		
	}
	
	// get all informations by sending request
	// once the last request is completed, swap the informations in bufferedAppInfo for the GUI
	public void loadMachineInfos() {
		if(state == State.SLEEPING)
			state = State.SENDING;
		
	}

	public UUID getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<? extends Class<? extends Channel>> getSupportedChannels() {
		return Collections.singleton(BigBrotherChannel.class);
	}

	public <C extends Channel> C getChannel(Class<C> channelClass,
			Object... params) {
		return channelClass.cast(this.bbChannel);
	}
	
	enum State {
		PAUSED,
		SENDING,
		WAITING_RESPONSE,
		SLEEPING;
	}
	
	public synchronized DataModel getBufferedAppInfo() {
		return bufferedAppInfo;
	}

	public synchronized void setBufferedAppInfo(DataModel bufferedAppInfo) {
		this.bufferedAppInfo = bufferedAppInfo;
	}

	public synchronized DataModel getProcessAppInfo() {
		return processAppInfo;
	}

	public synchronized void setProcessAppInfo(DataModel processAppInfo) {
		this.processAppInfo = processAppInfo;
	}

	public State getS() {
		return state;
	}

	public void setS(State state) {
		this.state = state;
	}

	public BigBrotherChannelImpl getBbChannel() {
		return bbChannel;
	}

}
