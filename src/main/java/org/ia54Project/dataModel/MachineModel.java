package org.ia54Project.dataModel;

import java.util.Collection;

public class MachineModel {
	String name;
	String ip;
	Collection<KernelModel> kernelList;


	public MachineModel() {
	}

	public MachineModel(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public Collection<KernelModel> getKernelList() {
		return kernelList;
	}

	public void setKernelList(Collection<KernelModel> kernelList) {
		this.kernelList = kernelList;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
