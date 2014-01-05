package org.ia54Project.dataModel;

import java.util.EventListener;

/**
 * A simple listener to allow listening on a dataModel
 * @author  Arnaud Roblin, Julien Benichou
 *
 */
public interface BigBrotherListener extends EventListener{
	 public void onDataChange(BigBrotherDataChangeEvent evt);
}
