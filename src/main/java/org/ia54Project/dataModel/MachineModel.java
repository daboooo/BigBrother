package org.ia54Project.dataModel;

import java.util.Vector;

public class MachineModel {
	String name;
	String ip;
	Vector<KernelModel> kernelList;


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
	
	
	public Vector<KernelModel> getKernelList() {
		return kernelList;
	}

	public void setKernelList(Vector<KernelModel> kernelList) {
		this.kernelList = kernelList;
	}
	
}
