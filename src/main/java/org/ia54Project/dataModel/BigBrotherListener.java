package org.ia54Project.dataModel;

import java.util.EventListener;

public interface BigBrotherListener extends EventListener{
	 public void onDataChange(BigBrotherDataChangeEvent evt);
}
