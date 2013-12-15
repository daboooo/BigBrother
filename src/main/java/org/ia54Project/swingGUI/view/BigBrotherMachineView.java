package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.ia54Project.dataModel.MachineModel;

public class BigBrotherMachineView extends BigBrotherDetailView {
	private static final long serialVersionUID = 5689658876841440199L;
	
	private static JLabel name_desc;
	private static JLabel ip_desc;
	private JLabel name;
	private JLabel ip;
	
	public BigBrotherMachineView(MachineModel machine) {
		name_desc 	= new JLabel("Host name: ");
		ip_desc 	= new JLabel("Ip :");
		name 		= new JLabel();
		ip 			= new JLabel();
		this.setLayout(new GridLayout(2, 2));
		this.add(name_desc);
		this.add(name);
		this.add(ip_desc);
		this.add(ip);
		this.setModel(machine);
		this.setVisible(true);
	}
	
	public void setModel(MachineModel machine) {
		ip.setText(machine.getIp());
		name.setText(machine.getName());
	}
	
}
