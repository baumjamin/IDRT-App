package routines;

import java.util.HashMap;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class enc_num_routine {
	public static HashMap<String, HashMap<String,Integer>> encVisitMap;
	public static HashMap<String, HashMap<String, Integer>> itemMap;
	
	public static void createMap() {
		itemMap = new HashMap<String, HashMap<String,Integer>>();
	}
	
	public static void destroy() {
		itemMap.clear();
		itemMap = null;
	}
	
	public static void createEncMap(){
		encVisitMap  = new HashMap<String, HashMap<String,Integer>>();
	}
	public static void destroyEncMap(){
		encVisitMap.clear();
		encVisitMap = null;
	}
	
public static int itemAnswer(String itemID, String itemValue) {
		
		if (itemMap.containsKey(itemID)){
			if (itemMap.get(itemID).containsKey(itemValue)){
			
				return itemMap.get(itemID).get(itemValue);
			}
			else {
//				System.out.println("size: "+ itemMap.get(itemID).size());
				itemMap.get(itemID).put(itemValue, itemMap.get(itemID).size());
				return itemMap.get(itemID).size()-1;
			}
		}
		else{
			HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
			tempMap.put(itemValue, 0);
			itemMap.put(itemID, tempMap);
			return 0;
			
		}
		
	}

//	public static String getEncNum(String encNum,String repeatKey){
//		return encVisitMap.
//	}
	
	public static String Enc_Visit_Num(String visit) {
		return Enc_Visit_Num(visit,"0");
	}
	public static String Enc_Visit_Num(String visit, String repeatKey) {
		int num = 0;
		int counter = 1;
//		System.out.println(visit);
		if (encVisitMap.containsKey(visit)){
//			System.out.println("true1");
			if (encVisitMap.get(visit).containsKey(repeatKey)){
//				System.out.println("true2");
				num = encVisitMap.get(visit).get(repeatKey);
//				System.out.println(num);
			}
			else{
//				System.out.println("else");
				counter = encVisitMap.get(visit).size()+1;
				HashMap<String, Integer> tmpHashMap = new HashMap<String, Integer>();
				tmpHashMap.put(repeatKey, counter);
				encVisitMap.get(visit).put(repeatKey, counter);
				num = counter;
			}
		}
		else{
//			System.out.println("else1");
			HashMap<String, Integer> tmpHashMap = new HashMap<String, Integer>();
			tmpHashMap.put(repeatKey, counter);
			encVisitMap.put(visit, tmpHashMap);
			num = counter;
//			System.out.println(num);
		}
		
		
//		System.out.println(encVisitMap);
		return ""+num;
	}
}
