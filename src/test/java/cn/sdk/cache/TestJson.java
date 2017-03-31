package cn.sdk.cache;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestJson {
	public static void main(String args[]){
		String json = "{'data':[{'name':'Wallace'},{'name':'Grommit'}]}";   
		try
		{
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for(int i=0;i<jsonArray.length(); i++){
				JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
				String name = jsonObjectItem.getString("name");
				System.out.println(name);
			}
		}
		catch(Exception e){
			
		}
		  

	}
}
