package org.ia54Project.swingGUI.view.standard;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ia54Project.dataModel.OrganizationModel;
import org.ia54Project.swingGUI.view.BigBrotherDetailView;

public class BigBrotherOrganizationView extends BigBrotherDetailView {
	private JTable tableDesc;
	private JTable tableValues;

	public BigBrotherOrganizationView() {
		initialize();
	}
	
	public BigBrotherOrganizationView(OrganizationModel model) {
		initialize();
		setModel(model);
	}
	
	public void setModel(OrganizationModel model) {
		tableDesc.getModel().setValueAt("Class :"			, 0, 0);
		tableDesc.getModel().setValueAt("Nb Instance :"		, 1, 0);
		tableValues.getModel().setValueAt(model.getClasse()	  , 0, 0);
		tableValues.getModel().setValueAt(model.getNbInstance(), 1, 0);
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JPanel panel = this;
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{456, -77};
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
		
		JLabel lblRoleDetails = new JLabel("Organization Details");
		panel_1.add(lblRoleDetails);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton button = new JButton("Stop");
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
		gbl_panel_3.columnWidths = new int[]{108, 263, 0};
		gbl_panel_3.rowHeights = new int[]{1, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		tableDesc = new JTable();
		tableDesc.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
			},
			new String[] {
				"Field"
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
			},
			new String[] {
				"Value"
			}
		));
		GridBagConstraints gbc_tableValues = new GridBagConstraints();
		gbc_tableValues.fill = GridBagConstraints.BOTH;
		gbc_tableValues.gridx = 1;
		gbc_tableValues.gridy = 0;
		panel_3.add(tableValues, gbc_tableValues);
	}
}
