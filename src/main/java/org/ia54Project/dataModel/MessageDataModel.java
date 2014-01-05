package org.ia54Project.dataModel;

import org.janusproject.kernel.message.AbstractContentMessage;

/**
 * Describe a message to carry a DataModel
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
public class MessageDataModel extends AbstractContentMessage<DataModel> {
	private static final long serialVersionUID = 2393489580038907400L;
	private final DataModel content;
	
	public MessageDataModel(DataModel content) {
		this.content = content;
	}
	
	@Override
	public DataModel getContent() {
		
		return content;
	}

}
