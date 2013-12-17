package org.ia54Project.dataModel;

import java.util.Collection;
import java.util.Vector;

public class MachineModel {
	
	private String name;
	private String ip;
	private Collection<KernelModel> kernelList;


	public MachineModel() {
		setName("Not fully initialized");
		setIp("Not fully initialized");
		setKernelList(new Vector<KernelModel>());
	}

	public MachineModel(String name, String ip) {
		setName(name);
		setIp(ip);
		setKernelList(new Vector<KernelModel>());
	}
	
	public MachineModel(String name, String ip, Collection<KernelModel> kernelList) {
		setName(name);
		setIp(ip);
		setKernelList(kernelList);
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
	
	public synchronized Collection<KernelModel> getKernelList() {
		return kernelList;
	}

	public synchronized void setKernelList(Collection<KernelModel> kernelList) {
		this.kernelList = kernelList;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
