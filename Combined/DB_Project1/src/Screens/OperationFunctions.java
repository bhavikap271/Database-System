package Screens;


import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class OperationFunctions {
	static String[] colnmI;
	static String validateMsg;

	public static void insertInTable(String tnm, JSONObject json) {

		JSONParser parser = new JSONParser();
		try {
			FileReader f1 =new FileReader("Data/Records/" + tnm + ".json");
			Object obj = parser.parse(f1);
			JSONObject json1 = (JSONObject) obj;
			System.out.println(json1.toJSONString());

			try {
				JSONArray headers = (JSONArray) json1.get("Records");
				headers.add(json);
				json1.put("Records", headers);
			} catch (ClassCastException e) {
				JSONArray JA = new JSONArray();
				JA.add(json);
				json1.put("Records", JA);
			}
			
			
			System.out.println(json1.toJSONString());

			File file = new File("Data/Records/" + tnm + ".json");
			FileWriter fw = null;
			BufferedWriter bw = null;
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write(json1.toJSONString());
			bw.flush();
			bw.close();
			f1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void updateInTable(String tnm, JSONObject json,String key,String value) {
		JSONParser parser = new JSONParser();
		try {
			FileReader f1 = new FileReader("Data/Records/" + tnm + ".json");
			Object obj = parser.parse(f1);
			JSONObject json1 = (JSONObject) obj;
			System.out.println(json1.toJSONString());
			
			try {
				JSONArray headers = (JSONArray) json1.get("Records");
				int rindex=-1;
				int size = headers.size();
				for(int i=0;i<size;i++)
				{
					JSONObject temp = (JSONObject) headers.get(i);
					if(temp.get(key).equals(value))
					{
						rindex = i;
						break;
					}
				}
				headers.remove(rindex);
				headers.add(rindex, json);
				json1.put("Records", headers);
			} catch (ClassCastException e) {
				JSONArray JA = new JSONArray();
				JA.add(json);
				json1.put("Records", JA);
			}
			
			
			System.out.println(json1.toJSONString());

			File file = new File("Data/Records/" + tnm + ".json");
			FileWriter fw = null;
			BufferedWriter bw = null;
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write(json1.toJSONString());
			bw.flush();
			bw.close();
			f1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	
	public static boolean validateJSON(String tnm, JSONObject json, boolean flag) {
		if(flag)
		{
			HashMap ColTypes =new HashMap<>();
			String key = GlobalData.tablePrimaryKeyMap.get(tnm);
			String[] col = null;
			int size=-1;
			String value1 = (String) json.get(key);
			if(value1.isEmpty())
			{
				validateMsg= "Primary Key value cannot be null";
				return false;
			}
			JSONParser parser1 = new JSONParser();
			try {
				FileReader f1 = new FileReader("Data/Records/" + tnm+ ".json");
				Object obj1 = parser1.parse(f1);
				JSONObject json12 = (JSONObject) obj1;
				
				
				
					JSONArray headers2 = (JSONArray) json12.get("Records");
					for(int i=0;i<headers2.size();i++)
					{
						JSONObject temp = (JSONObject) headers2.get(i);
						if(temp.get(key).equals(value1))
						{
							validateMsg= "Primary Key value "+key+" : "+value1+" already exists";
							return false;
						}
					}
					
				f1.close();	
			}
			catch(Exception e)
			{}
			JSONParser parser = new JSONParser();
			try {
				FileReader f1=new FileReader("Data/Metadata/"+tnm+".json");
				Object obj = parser.parse(f1);
				JSONObject json1 = (JSONObject) obj;			
				JSONArray headers = (JSONArray) json1.get("headers");			
				col = new String[headers.size()];
				size = headers.size();
				for (int i = 0; i < headers.size(); i++){
					
					Object temp = parser.parse(headers.get(i).toString());
					JSONObject temp1 = (JSONObject) temp;
					col[i]= (String) temp1.get("Column Name");
					String type = (String) temp1.get("Data Type");
					ColTypes.put(col[i], type);
					//System.out.println((String) temp1.get("Column Name"));
					
				}
				f1.close();
			}catch (Exception e) {	
				e.printStackTrace();
			}
		
			for(int i=0;i<size;i++)
			{
				switch((String)ColTypes.get(col[i]))
				{
				case "INT":
					try{
					int value = Integer.parseInt((String) json.get(col[i]));
					}
					catch(Exception e)
					{
						validateMsg = col[i]+" value not a valid integer";
						return false;
					}
					break;
				case "VARCHAR":
					break;
				case "FLOAT":
					try{
						float value = Float.parseFloat((String) json.get(col[i]));
						}
						catch(Exception e)
						{
							validateMsg = col[i]+" value not a valid float";
							return false;
						}
					break;
				case "Boolean":
					try{
						boolean value = Boolean.parseBoolean((String) json.get(col[i]));
						}
						catch(Exception e)
						{
							validateMsg = col[i]+" value not a valid boolean";
							return false;
						}
					break;
				}

			}
		
			return true;
		}
		else
		{

			HashMap ColTypes =new HashMap<>();
			String key = GlobalData.tablePrimaryKeyMap.get(tnm);
			String[] col = null;
			int size=-1;
			JSONParser parser = new JSONParser();
			try {
				FileReader f1 = new FileReader("Data/Metadata/"+tnm+".json");
				Object obj = parser.parse(f1);
				JSONObject json1 = (JSONObject) obj;			
				JSONArray headers = (JSONArray) json1.get("headers");			
				col = new String[headers.size()];
				size = headers.size();
				for (int i = 0; i < headers.size(); i++){
					
					Object temp = parser.parse(headers.get(i).toString());
					JSONObject temp1 = (JSONObject) temp;
					col[i]= (String) temp1.get("Column Name");
					String type = (String) temp1.get("Data Type");
					System.out.println(type);
					ColTypes.put(col[i], type);
					//System.out.println((String) temp1.get("Column Name"));
					
				}	
				f1.close();
			}catch (Exception e) {	
				e.printStackTrace();
			}
			
			for(int i=0;i<size;i++)
			{
				System.out.println(col[i]);
				System.out.println((String)ColTypes.get(col[i]));
				switch((String)ColTypes.get(col[i]))
				{
				case "INT":
					try{
					int value = Integer.parseInt((String) json.get(col[i]));
					}
					catch(Exception e)
					{
						validateMsg = col[i]+" value not a valid integer";
						return false;
					}
					break;
				case "VARCHAR":
					break;
				case "FLOAT":
					try{
						float value = Float.parseFloat((String) json.get(col[i]));
						}
						catch(Exception e)
						{
							validateMsg = col[i]+" value not a valid float";
							return false;
						}
					break;
				case "Boolean":
					try{
						boolean value = Boolean.parseBoolean((String) json.get(col[i]));
						}
						catch(Exception e)
						{
							validateMsg = col[i]+" value not a valid boolean";
							return false;
						}
					break;
				}

			}
		
			return true;
		
		}
		

	}


	public static void searchInTable(String tnm, JSONObject json) {

	}
}
