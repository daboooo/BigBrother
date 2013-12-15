package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

public class MessageKernelModel extends AbstractContentMessage<KernelModel> {
	private static final long serialVersionUID = 1933412044732372003L;
	private final KernelModel content;
	
	public MessageKernelModel(KernelModel content) {
		this.content = content;
	}
	
	@Override
	public KernelModel getContent() {
		return content;
	}
	 
}
