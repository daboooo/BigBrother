package org.ia54Project.dataModel;

import java.util.Collection;

public class DataModel {
	Collection<MachineModel> content;
	
	public DataModel() {
		
	}

	public DataModel(Collection<MachineModel> content) {
		super();
		this.content = content;
	}

	public Collection<MachineModel> getContent() {
		return content;
	}

	public void setContent(Collection<MachineModel> content) {
		this.content = content;
	}
	
}
