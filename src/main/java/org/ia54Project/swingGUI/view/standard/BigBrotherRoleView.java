package org.ia54Project.swingGUI.view.standard;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ia54Project.dataModel.RoleModel;
import org.ia54Project.swingGUI.view.BigBrotherDetailView;

public class BigBrotherRoleView extends BigBrotherDetailView {
	private JTable tableDesc;
	private JTable tableValues;
	private ActionListener listener;

	public BigBrotherRoleView() {
		initialize();
	}
	
	public BigBrotherRoleView(RoleModel role, ActionListener listener) {
		this.listener = listener;
		initialize();
		setModel(role);
	}
	
	public void setModel(RoleModel role) {
		tableDesc.getModel().setValueAt("Name :"			, 0, 0);
		tableDesc.getModel().setValueAt("RoleAdress :"		, 1, 0);
		tableDesc.getModel().setValueAt("GroupAddress :"	, 2, 0);

		if(role != null) {
			tableValues.getModel().setValueAt(role.toString()					, 0, 0);
			if(role.getRoleAddress() != null)
				tableValues.getModel().setValueAt(role.getRoleAddress().toString()	, 1, 0);
			if(role.getGroupAdress() != null) 
				tableValues.getModel().setValueAt(role.getGroupAdress().toString()	, 2, 0);
		} else {
			System.out.println("error gui: role null");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JPanel panel = this;
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{456, 0};
		gbl_panel.rowHeights = new int[]{24, 43, 235, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblRoleDetails = new JLabel("Role Details");
		panel_1.add(lblRoleDetails);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton button = new JButton("Kill");
		button.addActionListener(listener);
		button.setActionCommand("ORDER_KILL");
		panel_2.add(button);
		
		
		JButton button_1 = new JButton("Pause/Resume");
		panel_2.add(button_1);
		
		JButton button_2 = new JButton("Start");
		panel_2.add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		JPanel panel_3 = new JPanel();
		scrollPane.setViewportView(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{108, 125, 0};
		gbl_panel_3.rowHeights = new int[]{1, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		tableDesc = new JTable();
		tableDesc.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"New column"
			}
		));
		GridBagConstraints gbc_tableDesc = new GridBagConstraints();
		gbc_tableDesc.fill = GridBagConstraints.BOTH;
		gbc_tableDesc.insets = new Insets(0, 0, 0, 5);
		gbc_tableDesc.gridx = 0;
		gbc_tableDesc.gridy = 0;
		panel_3.add(tableDesc, gbc_tableDesc);
		
		tableValues = new JTable();
		tableValues.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"New column"
			}
		));
		GridBagConstraints gbc_tableValues = new GridBagConstraints();
		gbc_tableValues.fill = GridBagConstraints.BOTH;
		gbc_tableValues.gridx = 1;
		gbc_tableValues.gridy = 0;
		panel_3.add(tableValues, gbc_tableValues);
	}
}
