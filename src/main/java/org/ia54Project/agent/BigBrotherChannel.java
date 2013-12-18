package org.ia54Project.agent;

import java.util.Vector;

import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.MachineModel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.RoleAddress;

public interface BigBrotherChannel extends Channel{
	public String getFirstAgentName();
	public DataModel getMachineInfos();
	public void addBigBrotherListener(BigBrotherListener listener);
	public DataModel getData();
	public void switchPause();
	public void buildAndSendKill(AgentAddress agent);
	public void buildAndSendKill(RoleAddress agent);
	public void buildAndSendKill(GroupAddress groupToKill);
}
