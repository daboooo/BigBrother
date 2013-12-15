package org.ia54Project.agent;

import java.util.Vector;

import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.MachineModel;
import org.janusproject.kernel.channels.Channel;

public interface BigBrotherChannel extends Channel{
	public String getFirstAgentName();
	public DataModel getMachineInfos();
	public void addBigBrotherListener(BigBrotherListener listener);
	public DataModel getData();
	public void switchPause();
}
