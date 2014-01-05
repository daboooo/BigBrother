package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

/**
 * Describe a message to send an order
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
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
