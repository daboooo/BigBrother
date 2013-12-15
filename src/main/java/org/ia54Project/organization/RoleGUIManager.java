package org.ia54Project.organization;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;

import org.ia54Project.BigBrotherUtil;
import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageDataModel;
import org.janusproject.kernel.address.Address;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.channels.ChannelInteractable;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.crio.core.RoleAddress;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;

public class RoleGUIManager extends Role implements ChannelInteractable{

	private final BigBrotherChannelImpl bbChannel = new BigBrotherChannelImpl();
	private DataModel bufferedAppInfo = new DataModel(); // linked to the GUI
	private DataModel processAppInfo = new DataModel(); // used to get all informations
	private State state = State.SENDING;
	private State beforePauseState = state;
	private Timer timer;
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
		MachineModel m = null;
		try {
			m = new MachineModel(BigBrotherUtil.getComputerFullName(),InetAddress.getLocalHost ().getHostAddress ());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		// loading kernel
		Vector<KernelModel> kernelList = new Vector<KernelModel>();
		KernelModel k = new KernelModel("toto",Kernels.get().getAddress());
		kernelList.add(k);
		m.setKernelList(kernelList);
		Vector<MachineModel> machineList = new Vector<MachineModel>();
		machineList.add(m);
		print("mach infos");
		bufferedAppInfo.setContent(machineList);
		
		
		
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				loadMachineInfos();
			}
		};
		timer.schedule(task, 500, 20);
		return super.activate(params);
	}
	
	@Override
	public Status live() {
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

		// TODO
		public String getFirstAgentName() {
			
			return "yolo";
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


		
	}
	
	// swap the buffers and notify the UI
	public synchronized void swapGUIBuffers() {
			System.out.println("swap");
			bufferedAppInfo.setContent(processAppInfo.getContent());		
	}
	
	// get all informations by sending request
	// once the last request is completed, swap the informations in bufferedAppInfo for the GUI
	public void loadMachineInfos() {
		switch(state) {
		case SENDING:
			Message requestInfo = new StringMessage("request");
			Message response = null;
			state = State.WAITING_RESPONSE;
			print("sending request");
			sendMessage(RoleControlManager.class, requestInfo);
		break;
		case WAITING_RESPONSE:
			response = getMessage();
			if(response != null) {
				RoleAddress controlManager = getRoleAddress(getOrCreateGroup(OrganizationManager.class), RoleControlManager.class, RoleAddress.class.cast(response.getSender()).getPlayer());
				if(response instanceof MessageDataModel) {
					// we got a response
					print("got response");
					processAppInfo = MessageDataModel.class.cast(response).getContent();
					swapGUIBuffers();
					state = State.SENDING;
					
				}
			}
		break;
		default:
			
		}
		
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

	public void setBufferedAppInfo(DataModel bufferedAppInfo) {
		this.bufferedAppInfo = bufferedAppInfo;
	}

	public DataModel getProcessAppInfo() {
		return processAppInfo;
	}

	public void setProcessAppInfo(DataModel processAppInfo) {
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
