package org.ia54Project.agent;

import java.util.Vector;

import org.ia54Project.dataModel.BigBrotherListener;
import org.ia54Project.dataModel.DataModel;
import org.ia54Project.dataModel.MachineModel;
import org.janusproject.kernel.address.AgentAddress;
import org.janusproject.kernel.channels.Channel;
import org.janusproject.kernel.crio.core.GroupAddress;
import org.janusproject.kernel.crio.core.RoleAddress;

/**
 * Channel to allow communication between the gui and the role guiManager
 * this channel should be implemented withing roleGUIManager
 * @author Arnaud Roblin, Julien Benichou
 *
 */
public interface BigBrotherChannel extends Channel{
	public DataModel getMachineInfos();
	public void addBigBrotherListener(BigBrotherListener listener);
	public DataModel getData();
	public void switchPause();
	public void buildAndSendKill(AgentAddress agent);
	public void buildAndSendKill(RoleAddress role);
	public void buildAndSendKill(GroupAddress groupToKill);
}
