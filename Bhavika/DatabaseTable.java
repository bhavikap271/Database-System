package cse.buffalo.edu.swing;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.json.simple.JSONObject;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class DatabaseTable extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	static List<String> tableNames = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					DatabaseTable frame = new DatabaseTable();
					frame.setTitle("Create Table");
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatabaseTable() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 800, 300);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// add table here		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 41, 584, 111);
		
		final Class[] columnClass = new Class[] {
	            Boolean.class, Boolean.class, Boolean.class, String.class,String.class
	        };
	 
		final JTable table = new JTable(new DefaultTableModel(
		 	new Object[][] {
		 		{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "", "Choose.."},
		 	},
		 	new String[] {
		 		"Key", "Null", "Unique", "Column Name", "Data Type"
		 	}
		 	
		 	
		 ){
			
		public Class<?> getColumnClass(int columnIndex)
         {
             return columnClass[columnIndex];
         }});
		
		table.setBackground(Color.WHITE);
		
		// default values for dataType:
		//String [] val = {"Int","Varchar","Float"};
		table.setBounds(20, 53, 584, 100);		 
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		// add Jcombobox:
		String [] dataTypes = {"VARCHAR","INT","FLOAT"};
		JComboBox comboBox = new JComboBox(dataTypes);
		
		/// our combo column
	    TableColumn col = table.getColumnModel().getColumn(4);
	    col.setCellEditor(new DefaultCellEditor(comboBox));
		
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane); 	
		
		textField = new JTextField();
		textField.setBounds(107, 10, 497, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Table Name:");
		lblNewLabel.setFont(new Font("Perpetua", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 14, 77, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    //get data from the table and save it in json format:
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				int rowCount = dm.getRowCount();
				int colCount = dm.getColumnCount();
				
				String tableName  = textField.getText();
				System.out.println("Table Name:"+tableName);
				
				
				File file = new File(tableName+".json");
				FileWriter fw = null;
				BufferedWriter bw = null;
				
				
				try {
					fw = new FileWriter(file.getAbsoluteFile());
					 bw = new BufferedWriter(fw);
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
							
	            for(int i = 0;  i < rowCount ; i++){	
	            	
	            	  JSONObject json = new JSONObject();
	            	  
	            	  for(int j=0; j < colCount ; j++){	            		  
	            		    json.put(table.getColumnName(j), table.getModel().getValueAt(i, j));
	            	  }
	            	
	                 // write json data to file
	            	 try {
	            		 
	            	    System.out.println(json.toJSONString());	 
						bw.write(json.toString());
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	            
	            try {
					bw.flush();
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           
	           //save the tableName in globalList
	            GlobalData.allTables.add(tableName);
	            
	            
			}
		});
		
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));		
		btnNewButton.setBounds(557, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnNewButton_1.setBounds(678, 227, 96, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnAddColumn = new JButton("Add Column");
		btnAddColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					 DefaultTableModel dm = (DefaultTableModel)table.getModel();
					
					 Object [] rowData = {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,"","Choose.."};
					 dm.addRow(rowData);
				
			}
		});
		
		
		btnAddColumn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAddColumn.setBounds(651, 52, 123, 23);
		contentPane.add(btnAddColumn);
		
		JButton btnDeleteColumn = new JButton("Remove Column");
		btnDeleteColumn.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				// remove row
				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				dm.removeRow(table.getSelectedRow());				
			}
		});
		
		btnDeleteColumn.setBounds(651, 94, 123, 23);
		contentPane.add(btnDeleteColumn);
		
	}
	
}
