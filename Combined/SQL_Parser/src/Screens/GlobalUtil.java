package Screens;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GlobalUtil {
	
	
	
	public static boolean validateTableName(String name){
		
		boolean tableExists = true;
		
		//check if tableName exists
		File file = new File("Data/Metadata/"+name+".json");
		
		if(!file.exists())
			tableExists = false;
		
		return tableExists;
		
	}
	
	
	public static boolean validateColumnNames(List<String> columnNamesList, String tableName){
		
		FileReader f1;
		
		JSONParser parser = new JSONParser();
		ArrayList<String> columnNames = new ArrayList<String>();
		boolean isValidColName = true;
		
		try {
			
			f1 = new FileReader("Data/MetaData/" +tableName+ ".json");
			Object obj = parser.parse(f1);
			JSONObject json = (JSONObject) obj;
			JSONArray headers = (JSONArray) json.get("headers");
		
	    	for(int i = 0 ; i < headers.size(); i++){
	    		
	    		Object temp = parser.parse(headers.get(i).toString());
				JSONObject temp1 = (JSONObject) temp;			
				
				String columnName = (String) temp1.get("Column Name");
				columnNames.add(columnName);
								    	       	
	    	}
	    	
	    	//check if each in the sql exists in the columnList
			
			for(String colName : columnNamesList){					
				if(!columnNames.contains(colName)){
					
					isValidColName = false;
					break;
					
				}else
					continue;					
			}
	    	
	    	
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return isValidColName;
				
	}
		
	public static HashMap<String,String> fetchColumnNames(String tableName){
		FileReader f1;
		
		JSONParser parser = new JSONParser();
		HashMap<String,String> columnDetailMap = new HashMap<String,String>();
		
		
		try {
			
			f1 = new FileReader("Data/MetaData/" +tableName+ ".json");
			Object obj = parser.parse(f1);
			JSONObject json = (JSONObject) obj;
			JSONArray headers = (JSONArray) json.get("headers");
		
	    	for(int i = 0 ; i < headers.size(); i++){
	    		
	    		Object temp = parser.parse(headers.get(i).toString());
				JSONObject temp1 = (JSONObject) temp;			
				
				String columnName = (String) temp1.get("Column Name");
				String dataType = (String)temp1.get("Data Type");
				columnDetailMap.put(columnName, dataType);
								    	       	
	    	}
	    	
	    	//check if each in the sql exists in the columnList
			
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return columnDetailMap;
		
	}
	
	
	
	
	public static boolean validateDataType(String dataType, String val){
		
		switch(dataType)
		{
		case "INT":
			try{
			int value = Integer.parseInt(val);
			}
			catch(Exception e)
			{
				
				return false;
			}
			break;
		case "VARCHAR":
			break;
		case "FLOAT":
			try{
				float value = Float.parseFloat(val);
				}
				catch(Exception e)
				{
					return false;
				}
			break;

		}
		
	   return true;	
	}
	
	

}
