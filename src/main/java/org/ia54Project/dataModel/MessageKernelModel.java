package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

/**
 * Describe a message to carry a Kernel Model
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
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
