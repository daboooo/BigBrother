package org.ia54Project.organization;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
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
import org.ia54Project.dataModel.MessageMachineModel;
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
								BigBrotherUtil.printMachineModel(machineModel);
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
