package org.ia54Project.organization;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.ia54Project.BigBrotherUtil;
import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageDataModel;
import org.ia54Project.dataModel.MessageMachineModel;
import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.dataModel.RoleModel;
import org.janusproject.kernel.agentsignal.Signal;
import org.janusproject.kernel.agentsignal.SignalListener;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.agentsignal.SignalPolicy;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;

public class RoleControlManager extends Role{
	private Integer nbSend = 0;
	private final SignalListener signalListener = new MySignalListener();
	private State state;
	private long timeToRespond = 1000; // in millisecond
	private Timer timer;
	private TimerTask sendResponse;
	private Vector<KernelModel> kernelsReplied;
	private Vector<MachineModel> machineReplied;
	private long testStartTime;
	private long testWaitTime;
	boolean t1 = false;
	boolean t2 = false;
	boolean t3 = false;

//	RoleControlManager() {
//		kernelsReplied = new Vector<KernelModel> ();
//		timer = new Timer();
//		sendResponse = new TimerTask() {	
//			@Override
//			public void run() {
//				sendMessage(RoleGUIManager.class, new MessageDataModel(buildDataModel()));
//				state = State.LISTENNING;
//			}
//		};
//	}
	
	public Status activate(Object... parameters) {
	   getSignalManager().setPolicy(SignalPolicy.FIRE_SIGNAL);
	   addSignalListener(signalListener);
	   state = State.LISTENNING;
	   
	   return null;
	}
	
	@Override
	public Status live() {
		switch(state) {
		case LISTENNING:
			Message m = getMessage();
			if(m != null) {
				print("got message:" + m);
				RoleAddress guiManager = getRoleAddress(getOrCreateGroup(OrganizationController.class), RoleGUIManager.class, RoleAddress.class.cast(m.getSender()).getPlayer());
				if(m.getSender() == guiManager ) {
					if(m instanceof StringMessage) {
						// request of dataModel
						if(StringMessage.class.cast(m).getContent().equals("request")) {
							testStartTime = System.currentTimeMillis();
							state = State.SENDING;
							
//							state = State.BUILDING_DATAMODEL;
//							timer.schedule(sendResponse, timeToRespond);
						}
					}
					
				}
			}
		break;
		case SENDING:
			// send signal to all RoleManager
			testMode();
			
		break;
		case SLEEPING:
		break;
		default:
			
		}
		
		
		return null;
	}
	
	public void testMode() {
		// sending response
		
		if(System.currentTimeMillis() - testStartTime > 100 && !t1) {
			print("sending RESPONSE 1");
			sendMessage(RoleGUIManager.class, new MessageMachineModel(buildFakeData()));
			t1 = true;
			nbSend++;
			nbSend%=99999;
		}
		if(System.currentTimeMillis() - testStartTime > 500 && !t2) {
			print("sending RESPONSE 2");
			sendMessage(RoleGUIManager.class, new MessageMachineModel(buildFakeData()));
			t2 = true;
			nbSend++;
			nbSend%=99999;
		}
		if(System.currentTimeMillis() - testStartTime > 1200 && !t3) {
			print("sending RESPONSE 3");
			sendMessage(RoleGUIManager.class, new MessageMachineModel(buildFakeData()));
			nbSend++;
			nbSend%=99999;
			t1 = false;
			t2 = false;
			t3 = false;
			state= State.LISTENNING;
		}
	}
	
	public DataModel buildDataModel() {
		DataModel data = new DataModel();
		data.setContent(buildMachineModels());
		return data;
		
	}
	
	public Collection<MachineModel> buildMachineModels(){
		Collection<MachineModel> machines = new Vector<MachineModel>();
		MachineModel mm = new MachineModel();
		
		return null;
		
	}
	
	public MachineModel buildFakeData() {
		DataModel datam = new DataModel();
		// MachineModel
		MachineModel mm = null;
		try {
			mm = new MachineModel(BigBrotherUtil.getComputerFullName(),InetAddress.getLocalHost ().getHostAddress ());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//fake agent coll
		Vector<AgentModel> agentList = new Vector<AgentModel>();
			// fake agent
			AgentModel a = new AgentModel("Bond" + (Integer)(nbSend-10), null);
			a.setAddress(getPlayer());
			a.setCanCommitSuicide(true);
			a.setCreationDate((float)22.3);
			a.setCreatorAddress(Kernels.get().getAddress());
			a.setIsAlive(true);
			a.setIsCompound(false);
			a.setIsHeavyAgent(true);
			a.setIsRecruitementAllowed(false);
			a.setIsSleeping(false);
			
			Vector<String> listOfRole = new Vector<String>();
			listOfRole.add(getClass().toString());
			listOfRole.add("Role controller");
			a.setListOfRole(listOfRole);
			
			
			AgentModel a1 = new AgentModel("James",null);
			AgentModel a2 = new AgentModel("Lulu",null);
			agentList.add(a);
			agentList.add(a1);
			agentList.add(a2);
		
		//fake role coll
		Vector<RoleModel> roleList = new Vector<RoleModel>();
			//fake role
			RoleModel faker = new RoleModel(getClass());
			faker.setRoleAddress(getAddress());
			faker.setGroupAdress(getGroupAddress());
			faker.setHasMesage(hasMessage());
			faker.setIsReleased(isReleased());
			faker.setIsSleeping(isSleeping());	
			faker.setPlayerList(agentList);
			
			RoleModel faker2 = new RoleModel(RoleCollecteur.class);
			
			roleList.add(faker);
			roleList.add(faker2);
				
		//fake groupModel
		Vector<GroupModel> groupList = new Vector<GroupModel>();
			GroupModel group = new GroupModel(null);
			group.setGroupAddress(getGroupAddress());
			group.setRoleList(roleList);
			groupList.add(group);
			
		//fake org coll
			Vector<OrganizationModel> orgList = new Vector<OrganizationModel>();
			//fake org
			OrganizationModel fakeo = new OrganizationModel(OrganizationController.class);
			fakeo.setGroupList(groupList);
			fakeo.setNbInstance(1);
			orgList.add(fakeo);
			
		
		//fake lonelyAgent
			Vector<AgentModel> llAgentList = new Vector<AgentModel>();
			AgentModel fakela = new AgentModel("grosfake lonely",null);
			AgentModel fakela2 = new AgentModel("badministrator",null);
			llAgentList.add(fakela);
			llAgentList.add(fakela2);
		
		
		// fake kList
		Vector<KernelModel> kernelList = new Vector<KernelModel>();
			KernelModel k = new KernelModel("v"+nbSend+ " :P",Kernels.get().getAddress());
			k.setKernelAddress(Kernels.get().getAddress());
			k.setOrgList(orgList);
			k.setLonelyAgentList(llAgentList);
			kernelList.add(k);
		
		
		mm.setKernelList(kernelList);
		Vector<MachineModel> machineList = new Vector<MachineModel>();
		machineList.add(mm);
		datam.setContent(machineList);
		return (MachineModel) ((Vector<MachineModel>) datam.getAllMachines()).get(0);
	}

	private class MySignalListener implements SignalListener {
		public void onSignal(Signal signal) {
			
			
		}
	}
	
	enum State {
		LISTENNING,
		SLEEPING, SENDING
	}
	

}
