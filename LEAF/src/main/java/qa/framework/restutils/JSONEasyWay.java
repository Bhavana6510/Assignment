package qa.framework.restutils;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import qa.framework.utils.FileManager;

public class JSONEasyWay {

	String value="attribute not found";

	
	@Test
	public void test() {
		
		String filePath ="./src/test/resources/demo/json/jsoneasyway-demo.json";
		
		String source =FileManager.readFile(filePath);
		
		System.out.println(getAttribute(source, "data",-1));
		
		
	}

	public  String getAttribute(String json, String attributeName, int index) {
		
		
		json=json.replace("/n", "");
		String[] attributes = attributeName.split("\\.");
		
		for(int i=0; i<attributes.length; i++) {
			
			
			if(i==0) {
				
				if(json.startsWith("{")) {
					
					JSONObject obj = new JSONObject(json);
					getObjectAttributeValue(obj,attributes[i]);				
					
				}else {
					JSONArray jsonArr = new JSONArray(json);
					getArrayAttributeValue(jsonArr,attributes[i]);
					
				}
				
				
			}else {
				if(value.startsWith("{")) {
					JSONObject objValue = new JSONObject(value);
					value ="attribute not found";
					getObjectAttributeValue (objValue, attributes[i]);
				}
			}
			
		}
		
		if(!value.equals("attribute not found") && index>-1) {
			
			if(value.contains("|")) {
				/*in case array value is like: 00|001|002*/
				value = value.split("\\|")[index];
			}else if(value.startsWith("[") && !value.contains("{")) {
				/*in case array value is like: ["00","62"]*/
					JSONArray jsonArr = new JSONArray(value);
					value = jsonArr.getString(index).toString();
			}else if(value.startsWith("[") && value.contains("{")) {
				/* in case array value is like: 
				 * [{"apple":"red","orange":"orange"},{"leaf":"green"}]
				 * OR*************************************************
				 * [{"apple":"red","inside":{"seed":"black","flesh":"white"}},{"leaf":"green"}]*/
				
				JSONArray jsonArr = new JSONArray(value);
				value = jsonArr.getJSONObject(index).toString();
				
				
			}
		}
		return value;
		
	}
	
	private  void getArrayAttributeValue(JSONArray jsonArr, String attributeName) {
		if(!jsonArr.isEmpty()) {
			Iterator<Object> arrIterator = jsonArr.iterator();
			
			while(arrIterator.hasNext()) {
				/*"color":["blue","while"], then valueInsideArr="blue","white"*/
				/*"color":[{"blue"},{"white"}], then valueInsideArr={"blue"},{"white"}*/
				String valueInsideArr = arrIterator.next().toString();
				
				if(valueInsideArr.startsWith("{")) {
					getObjectAttributeValue(new JSONObject(valueInsideArr),attributeName);
				}
				
			}
		}
	}
	
	private  void getObjectAttributeValue(JSONObject jsonObj, String attributeName) {
		Iterator<String> keys = jsonObj.keys();
		while(keys.hasNext()) {
			String currentNode = keys.next().toString();
			String isValue = jsonObj.get(currentNode).toString();
			
			if(currentNode.equals(attributeName)) {
				if(value.equals("attribute not found")) {
					value=isValue;
				}else {
					value = value+"|"+isValue;
				}
			}else if(isValue.startsWith("{")) {
				getObjectAttributeValue(jsonObj.getJSONObject(currentNode), attributeName);
			}else if(isValue.startsWith("[")) {
				JSONArray jsonArray = jsonObj.getJSONArray(currentNode);
				getArrayAttributeValue(jsonArray,attributeName);
			}else {
				// blank
			}
		}
	}

}
