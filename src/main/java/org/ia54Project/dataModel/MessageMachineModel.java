package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

/**
 * Describe a message to carry a MachineModel
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
public class MessageMachineModel extends AbstractContentMessage<MachineModel> {
	private static final long serialVersionUID = 2393489580038907400L;
	private final MachineModel content;
	
	public MessageMachineModel(MachineModel content) {
		this.content = content;
	}
	
	@Override
	public MachineModel getContent() {
		
		return content;
	}

}
