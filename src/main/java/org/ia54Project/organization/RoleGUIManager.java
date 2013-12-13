package org.ia54Project.organization;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import org.ia54Project.agent.BigBrotherChannel;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageDataModel;
import org.janusproject.kernel.address.Address;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.channels.ChannelInteractable;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;

public class RoleGUIManager extends Role implements ChannelInteractable{

	private final BigBrotherChannelImpl bbChannel = new BigBrotherChannelImpl();
	
	private DataModel bufferedAppInfo = new DataModel(); // linked to the GUI
	private DataModel processAppInfo = new DataModel(); // used to get all informations
	private boolean t = true;
	@Override
	public Status live() {
		if(t) {
			t = false;
			loadMachineInfos();
		}
	
		return null;
	}
	
	private class BigBrotherChannelImpl implements BigBrotherChannel {

		public Address getChannelOwner() {
			return getAddress();
		}

		// TODO
		public String getFirstAgentName() {
			
			return "yolo";
		}

		
		public Vector<MachineModel> getMachineInfos() {

//			MachineModel m = null;
//			try {
//				m = new MachineModel(BigBrotherUtil.getComputerFullName(),InetAddress.getLocalHost ().getHostAddress ());
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			}
//			
//			// loading kernel
//			Vector<KernelModel> kernelList = new Vector<KernelModel>();
//			KernelModel k = new KernelModel("toto",Kernels.get().getAddress());
//			kernelList.add(k);
//			m.setKernelList(kernelList);
//			machineList.add(m);
//			print("mach infos");
//			return machineList;
			return null;
		}
		
	}
	
	// get all informations by sending request
	// once the last request is completed, swap the informations in bufferedAppInfo for the GUI
	public void loadMachineInfos() {
		Message requestInfo = new StringMessage("request");
		Message response = null;
		Boolean waitResponse = true;
		print("sending request");
		sendMessage(RoleControlManager.class, requestInfo);
		while(waitResponse) {
			//print("waiting response");
			response = getMessage();
			if(response != null) {
				if(response.getSender() == getRoleAddress(RoleControlManager.class) && response instanceof MessageDataModel) {
					// we got a response
					print("got response");
					processAppInfo = MessageDataModel.class.cast(response).getContent();
					waitResponse = false;
				}
			}
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
//		
//		if (BigBrotherChannel.class.isAssignableFrom(channelClass)) {
//			 
//		      // Create the instance of the channel.
//			BigBrotherChannel channelInstance = new BigBrotherChannelImpl();
//		 
//		      // Reply the channel instance.
//		      return channelClass.cast(channelInstance);
//		 
//		    }
//		// The given channel type is not supported
//	    throw new IllegalArgumentException("channelClass");
		return channelClass.cast(this.bbChannel);
	}

}
