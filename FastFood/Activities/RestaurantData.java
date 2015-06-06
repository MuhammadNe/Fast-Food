package com.MOS.fastfood;

import java.util.ArrayList;

public class RestaurantData {

	static ArrayList<String> Data = new ArrayList<String>();
	static ArrayList<String> lng = new ArrayList<String>();
	static ArrayList<String> lat = new ArrayList<String>();
	static ArrayList<String> name = new ArrayList<String>();
	static ArrayList<String> comment = new ArrayList<String>();
	static String savelng;
	static String saveLat;
	static String saveName = "";
	

	public void parse(String word)
	{
		int startPosition = word.indexOf("?name?") + "?name?".length();
		int endPosition = word.indexOf("?/name?", startPosition);
		name.add(word.substring(startPosition, endPosition));
		
		int startPosition2 = word.indexOf("?lng?") + "?lng?".length();
		int endPosition2 = word.indexOf("?/lng?", startPosition2);
		lng.add(word.substring(startPosition2, endPosition2));
		
		int startPosition3 = word.indexOf("?lat?") + "?lat?".length();
		int endPosition3 = word.indexOf("?/lat?", startPosition3);
		lat.add(word.substring(startPosition3, endPosition3));

	}

	//hi?/?15?/?14?/?





}
