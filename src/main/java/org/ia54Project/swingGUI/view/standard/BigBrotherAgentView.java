package org.ia54Project.swingGUI.view.standard;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ia54Project.dataModel.AgentModel;
import org.ia54Project.swingGUI.view.BigBrotherDetailView;

public class BigBrotherAgentView extends BigBrotherDetailView{

	private JPanel panelDetailView;
	private JTable tableRolesList;
	private JTable tableValues;
	private JTable tableDesc;

	/**
	 * Create the application.
	 */
	public BigBrotherAgentView() {
		initialize();
	}
	
	public BigBrotherAgentView(AgentModel agent) {
		initialize();
		setModel(agent);
		setVisible(true);
		System.out.println("construct");
	}
	
	public void setModel(AgentModel agent) {
		System.out.println("set model");
		Float date = agent.getCreationDate();
		
		//tableDesc.getModel().
	
		tableDesc.getModel().setValueAt("Name :", 0, 0);
		tableDesc.getModel().setValueAt("Address :", 1, 0);
		tableDesc.getModel().setValueAt("Creation date :", 2, 0);
		tableDesc.getModel().setValueAt("Execution ressource type :", 3, 0);
		tableDesc.getModel().setValueAt("isAlive :", 4, 0);
		tableDesc.getModel().setValueAt("isCompound : ", 5, 0);
		tableDesc.getModel().setValueAt("recruitement capacity :", 6, 0);
		tableDesc.getModel().setValueAt("isSleeping :", 7, 0);
		tableDesc.getModel().setValueAt("can commit suicide :", 8, 0);

		tableValues.getModel().setValueAt(agent.toString()								, 0, 0);
		tableValues.getModel().setValueAt(agent.getAddress().toString()					, 1, 0);
		tableValues.getModel().setValueAt(date.toString()								, 2, 0);
		tableValues.getModel().setValueAt(agent.getIsHeavyAgent().toString()	  		, 3, 0);
		tableValues.getModel().setValueAt(agent.getIsAlive().toString()					, 4, 0);
		tableValues.getModel().setValueAt(agent.getIsCompound().toString()				, 5, 0);
		tableValues.getModel().setValueAt(agent.getIsRecruitementAllowed().toString()	, 6, 0);
		tableValues.getModel().setValueAt(agent.getIsSleeping().toString()				, 7, 0);
		tableValues.getModel().setValueAt(agent.getCanCommitSuicide().toString()	  	, 8, 0);
		
		Vector<String> roles = agent.getListOfRole();
		if(roles != null) {
			if( roles.size() == tableRolesList.getModel().getRowCount()) {
				for (int i = 0 ; i < roles.size() ; i++) {
					tableRolesList.getModel().setValueAt(roles.get(i), i, 0);
				}
			} else {
				String[][] rolesTab = new String[roles.size()][1];
				for (int i = 0 ; i < roles.size() ; i++) {
					rolesTab[i][0] = roles.get(i);
				}	
				tableRolesList.setModel(new DefaultTableModel(rolesTab,new String[] {"Roles played"}));
			}
			
		}
	}


	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JPanel panelDetailView = this;
		panelDetailView.setLayout(new BoxLayout(panelDetailView, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panelDetailView.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{456, 0};
		gbl_panel.rowHeights = new int[]{24, 43, 235, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		JLabel lblAgentDetails = new JLabel("Agent Details");
		panel_1.add(lblAgentDetails);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnStop = new JButton("Stop");
		panel_2.add(btnStop);
		
		JButton btnPauseresume = new JButton("Pause/Resume");
		panel_2.add(btnPauseresume);
		
		JButton btnStart = new JButton("Start");
		panel_2.add(btnStart);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		JPanel panel_3 = new JPanel();
		scrollPane.setViewportView(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{182, 70, 0};
		gbl_panel_3.rowHeights = new int[]{128, 150, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		tableDesc = new JTable();
		tableDesc.setRowSelectionAllowed(false);
		tableDesc.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Fields"
			}
		));
		GridBagConstraints gbc_tableDesc = new GridBagConstraints();
		gbc_tableDesc.insets = new Insets(0, 0, 5, 5);
		gbc_tableDesc.fill = GridBagConstraints.BOTH;
		gbc_tableDesc.gridx = 0;
		gbc_tableDesc.gridy = 0;
		panel_3.add(tableDesc, gbc_tableDesc);
		
		tableValues = new JTable();
		tableValues.setRowSelectionAllowed(false);
		tableValues.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Value"
			}
		));
		GridBagConstraints gbc_tableValues = new GridBagConstraints();
		gbc_tableValues.insets = new Insets(0, 0, 5, 0);
		gbc_tableValues.fill = GridBagConstraints.BOTH;
		gbc_tableValues.gridx = 1;
		gbc_tableValues.gridy = 0;
		panel_3.add(tableValues, gbc_tableValues);
		
		tableRolesList = new JTable();
		tableRolesList.setRowSelectionAllowed(false);
		GridBagConstraints gbc_tableRolesList = new GridBagConstraints();
		gbc_tableRolesList.gridwidth = 2;
		gbc_tableRolesList.fill = GridBagConstraints.BOTH;
		gbc_tableRolesList.gridx = 0;
		gbc_tableRolesList.gridy = 1;
		panel_3.add(tableRolesList, gbc_tableRolesList);
		tableRolesList.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"Role List"
			}
		));
	}
	
	
}
