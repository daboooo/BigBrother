package org.ia54Project.dataModel;

import java.util.EventObject;

/**
 * This event  is used when changes occurs on the dataModel, to update the gui
 * @author Arnaud Roblin, Julien Benichou
 *
 */
public class BigBrotherDataChangeEvent extends EventObject {
	private static final long serialVersionUID = -601021189475156147L;

	public BigBrotherDataChangeEvent(DataModel arg0) {
		super(arg0);
	}

}
