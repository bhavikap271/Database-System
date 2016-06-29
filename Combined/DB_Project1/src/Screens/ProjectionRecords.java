package Screens;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProjectionRecords {

	private JFrame frame;
	private JTable table;
	HashMap<String,String> columnDataType;
	
	/**
	 * Launch the application.
	 */
	public void projectRecords(String tableName, ArrayList<String> SelectedAttributes) {
		

		try {
			// populate records in the table
			frame.setTitle(tableName);
			populateRecords("Data/Records/" + tableName + ".json", SelectedAttributes);

			// display all the records of this table

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void projectRecords(String tableName) {
		//EventQueue.invokeLater(new Runnable() {
			//public void run() {
		try {

			ProjectionRecords window = new ProjectionRecords();
			System.out.println(tableName);

			// populate records in the table
			window.frame.setTitle(tableName);
			window.populateRecords("Data/Records/" + tableName + ".json");

			// display all the records of this table

		} catch (Exception e) {
			e.printStackTrace();
		}
   }


	public void populateRecords(String name, ArrayList<String> SelectedAttributes) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(name));
			JSONObject json = (JSONObject) obj;
			JSONArray headers = (JSONArray) json.get("Records");

			if (headers.size() == 0) {
				JOptionPane.showMessageDialog(null, "No Records to Display", "Warning",JOptionPane.INFORMATION_MESSAGE);
			} else {

				String[] columnNames = new String[SelectedAttributes.size()];
				SelectedAttributes.toArray(columnNames);
				
				getDataTypes(frame.getTitle());
				
				table = new JTable();
				table.setModel(new DefaultTableModel(new Object[][] {}, columnNames) {

					@Override
					public boolean isCellEditable(int row, int col) {

						return false;
					}
					
					public Class<?> getColumnClass(int columnIndex){
		    			
						String columnName = getColumnName(columnIndex);
						
						String dataType  = columnDataType.get(columnName);
						
						if("VARCHAR".equals(dataType))
							 return String.class;
						else if("INT".equals(dataType))
							 return Integer.class;
						else if("FLOAT".equals(dataType))
							 return BigDecimal.class;

						return String.class;

					}   

				});

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

				for (int i = 0; i < headers.size(); i++) {

					Object temp = parser.parse(headers.get(i).toString());
					JSONObject currJson = (JSONObject) temp;

					Object[] data = new Object[columnNames.length];
					
					int index = 0;
					for (String key : columnNames){	
						
						String dataType = columnDataType.get(key);
						if("INT".equals(dataType))
							data[index] = Integer.parseInt((String)currJson.get(key));
						else if("FLOAT".equals(dataType))
							data[index] = new BigDecimal((String)currJson.get(key));
						else 
							data[index] = (String)currJson.get(key);
						
						index++;
					}
					
					tableModel.addRow(data);
				}

				table.setFillsViewportHeight(true);
				table.setAutoCreateRowSorter(true);
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(37, 5, 468, 100);
				scrollPane.setViewportView(table);
				scrollPane.setPreferredSize(new Dimension(468, 100));
				frame.getContentPane().add(scrollPane);

				this.frame.setVisible(true);

			}

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ParseException e) {
			e.printStackTrace();
		}
	}

	public void populateRecords(String name) {
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader(name));
			JSONObject json = (JSONObject) obj;
			JSONArray headers = (JSONArray) json.get("Records");
			System.out.println(headers.toString());

			if (headers.size() == 0) {

				JOptionPane.showMessageDialog(null, "No Records to Display", "Warning",
						JOptionPane.INFORMATION_MESSAGE);

			} else {

				Object temp = parser.parse(headers.get(0).toString());
				JSONObject currJson = (JSONObject) temp;
				System.out.println("test!!!!" + headers.get(0).toString());
				Set<String> keys = currJson.keySet();
				String[] columnNames = keys.toArray(new String[keys.size()]);
				
				getDataTypes(frame.getTitle());
				
				//for (int i = 0; i < columnNames.length; i++)
					//   System.out.println(i+"th:" + columnNames[i]);

				table = new JTable();

				table.setModel(new DefaultTableModel(new Object[][] {}, columnNames) {

					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
					
					public Class<?> getColumnClass(int columnIndex){
		    			
						String columnName = getColumnName(columnIndex);
						
						String dataType  = columnDataType.get(columnName);
						
						if("VARCHAR".equals(dataType))
							 return String.class;
						else if("INT".equals(dataType))
							 return Integer.class;
						else if("FLOAT".equals(dataType))
							 return BigDecimal.class;

						return String.class;

					}   
					
				});

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

				for (int i = 0; i < headers.size(); i++) {
					temp = parser.parse(headers.get(i).toString());
					currJson = (JSONObject) temp;

					Object[] data = new Object[columnNames.length];
					int index = 0;
					for (String key : keys) {
						
						String dataType = columnDataType.get(key);
						if("INT".equals(dataType))
							data[index] = Integer.parseInt((String)currJson.get(key));
						else if("FLOAT".equals(dataType))
							data[index] = new BigDecimal((String)currJson.get(key));
						else 
							data[index] = (String)currJson.get(key);
						
						index++;
						
					}
					
					tableModel.addRow(data);
				}

				table.setFillsViewportHeight(true);
				table.setAutoCreateRowSorter(true);
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(37, 5, 468, 100);
				scrollPane.setViewportView(table);
				scrollPane.setPreferredSize(new Dimension(468, 100));
				frame.getContentPane().add(scrollPane);

				this.frame.setVisible(true);

				
			}

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public ProjectionRecords() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 738, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	private void getDataTypes(String tableName){
		
		 JSONParser parser = new JSONParser();
		 columnDataType = new HashMap<String,String>();
		 		 
			try {
				FileReader f1= new FileReader("Data/MetaData/" + tableName + ".json");
				Object obj = parser.parse(f1);
				JSONObject json = (JSONObject) obj;
				JSONArray headers = (JSONArray) json.get("headers");
			
		    	for(int i = 0 ; i < headers.size(); i++){
		    		
		    		Object temp = parser.parse(headers.get(i).toString());
					JSONObject temp1 = (JSONObject) temp;			
					String dataType = (String) temp1.get("Data Type");
					String columnName = (String) temp1.get("Column Name");
					
					columnDataType.put(columnName, dataType);
					System.out.println("Data Types: "+columnDataType);
						    	       	
		    	}
		    	
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e){
				e.printStackTrace();
			}	
		}
	
	
}