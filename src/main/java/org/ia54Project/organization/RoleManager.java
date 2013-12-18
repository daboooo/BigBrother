package org.ia54Project.organization;

import java.util.Collection;

import org.ia54Project.BigBrotherUtil;
import org.ia54Project.dataModel.GroupModel;
import org.ia54Project.dataModel.KernelModel;
import org.ia54Project.dataModel.MachineModel;
import org.ia54Project.dataModel.MessageMachineModel;
import org.ia54Project.dataModel.MessageOrder;
import org.ia54Project.dataModel.Order;
import org.ia54Project.dataModel.OrganizationModel;
import org.janusproject.kernel.agentsignal.Signal;
import org.janusproject.kernel.agentsignal.SignalListener;
import org.janusproject.kernel.agentsignal.SignalPolicy;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.message.StringMessage;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;


public class RoleManager  extends Role{
	private final SignalListener signalListener = new MySignalListener();
	private Boolean parsing = false;
	
	@Override
	public Status activate(Object... parameters) {
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityGetAgentRepository.class));
		getSignalManager().setPolicy(SignalPolicy.FIRE_SIGNAL);
		addSignalListener(signalListener);
		
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		Message message = getMessage();
		
		// Si on reçoit un message de type MessageMachineModel c'est qu'il est envoyé par le collecteur
		// Donc on envoie un signal contenant ce MessageKernelModel
		// celui-ci sera récupéré par le role controlManager
		if(message != null && message instanceof MessageMachineModel) {
			BigBrotherUtil.printMachineModel(MessageMachineModel.class.cast(message).getContent());
			Signal signal = new Signal(this, "SIGNAL_RESPONSE", message);
			getSignalManager().fireSignal(signal);
		} 
		
		return StatusFactory.ok(this);
	}
	
	private class MySignalListener implements SignalListener {
		// Lorsqu'on reçoit un signal SIGNAL_REQUEST, celui-ci provient du role controlManager
		// Cela signifie qu'il demande des informations
		// On envoie donc un message au collecteur afin qu'il les recupere
		public void onSignal(Signal signal) {
			if(signal.getName().equals("SIGNAL_REQUEST")) {
				//print("ANY REQUEST MOFO");
				sendMessage(RoleCollecteur.class, new StringMessage("request"));
			} else if(signal.getName().equals("ORDER_REQUEST")) {
				// forward to the executant
				Object order =  signal.getValues()[0];
				if(order instanceof MessageOrder) {
					sendMessage(RoleExecutant.class, (Message) order);
				}
			}
		}
	}
}
