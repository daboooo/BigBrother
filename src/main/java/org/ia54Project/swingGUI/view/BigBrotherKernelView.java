package org.ia54Project.swingGUI.view;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.ia54Project.dataModel.KernelModel;

public class BigBrotherKernelView extends BigBrotherDetailView {
	private static final long serialVersionUID = 2535892955045128764L;
	private static JLabel name_desc;
	private static JLabel kernelAdress_desc;
	private JLabel name;
	private JLabel kernelAdress;
	
	public BigBrotherKernelView(KernelModel kernel) {
		name_desc = new JLabel("Name :");
		kernelAdress_desc = new JLabel("Address :");
		name = new JLabel();
		kernelAdress = new JLabel();
		
		this.setLayout(new GridLayout(2, 2));
		this.add(name_desc);
		this.add(name);
		this.add(kernelAdress_desc);
		this.add(kernelAdress);
		setModel(kernel);
		this.setVisible(true);
	}
	
	public void setModel(KernelModel kernel) {
		name.setText(kernel.getName());
		kernelAdress.setText(kernel.getKernelAddress().toString());
	}

}
