package org.ia54Project.organization;

import org.ia54Project.dataModel.MessageOrder;
import org.ia54Project.dataModel.Order;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.agent.Kernels;
import org.janusproject.kernel.crio.core.HasAllRequiredCapacitiesCondition;
import org.janusproject.kernel.crio.core.Role;
import org.janusproject.kernel.message.Message;
import org.janusproject.kernel.status.Status;
import org.janusproject.kernel.status.StatusFactory;

public class RoleExecutant extends Role{
	
	@Override
	public Status activate(Object... parameters) { 	
		addObtainCondition(new HasAllRequiredCapacitiesCondition(CapacityKillAgent.class));
		return StatusFactory.ok(this);
	}
	
	@Override
	public Status live() {
		//print("Je suis dans le role executant");
		Message message = getMessage();
		if(message != null) {
			if(message instanceof MessageOrder) {
				Order order = MessageOrder.class.cast(message).getContent();
				System.out.println("Order to execute = " + order.getAction() + " on: " + order.getAgents());
				executeOrder(order);
			}
		}
		return null;
	}
	
	public void executeOrder(Order order) {
		switch(order.getAction()) {
		case KILL:	
			for (AgentAddress agentAddress : order.getAgents()) {
				try {
					executeCapacityCall(CapacityKillAgent.class, agentAddress);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

}
