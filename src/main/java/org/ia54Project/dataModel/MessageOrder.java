package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

public class MessageOrder extends AbstractContentMessage<Order>{
	Order order;
	
	public MessageOrder(Order order) {
		this.order = order;
	}

	
	@Override
	public Order getContent() {
		// TODO Auto-generated method stub
		return order;
	}

}
