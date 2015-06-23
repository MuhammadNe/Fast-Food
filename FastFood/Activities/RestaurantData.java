package com.MOS.fastfood;

import java.util.ArrayList;

public class RestaurantData {

	//******* Arrays that holds information of restaurants taken from SERVER
	static public ArrayList<String> name = new ArrayList<String>();
	static public ArrayList<String> lng = new ArrayList<String>();
	static public ArrayList<String> lat = new ArrayList<String>();
	static public ArrayList<String> address = new ArrayList<String>();
	static public ArrayList<String> rate = new ArrayList<String>();
	static public ArrayList<String> comment = new ArrayList<String>();
	
	//******* in ListActivity for chosing a restaurant to view
	static public int restaurantNumber;
	static public String setName;
	static public String setAddress;
	static public String setRating;
	
	
	//******* in MapActivity for adding a new restaurant
	static public String savelng;
	static public String saveLat;
	static public String saveName = "";
	static public String saveComment = "";
	static public String saveAddress = "";
	static public String saveRate = "";
	
	
	static public int numOfRes;
	
	
	boolean checkIfExist;
	public void restaurantParse(String toParse)
	{
		while(!toParse.equals("") && toParse.length() > 0)
		{
			checkIfExist = false;
			String resTemp;
			resTemp = toParse.substring(0, toParse.indexOf("#"));
			//System.out.println(resTemp);
			String[] parsed = resTemp.split(",");
			
			for(int i = 0; i<name.size(); i++)
			{
				if(name.get(i).equals(parsed[0])
						&& address.get(i).equals(parsed[2])
						&& lng.get(i).equals(parsed[3])
						&& lat.get(i).equals(parsed[4])
						)
				{
					checkIfExist = true;
				}
			}
			if(!checkIfExist)
			{
				name.add(parsed[0]);
				address.add(parsed[2]);
				lng.add(parsed[3]);
				lat.add(parsed[4]);
				try{
				rate.add(parsed[5]);
				}
				catch(Exception e)
				{
					rate.add("0");
				}
			}
			/*name.add(parsed[0]);
			lng.add(parsed[1]);
			lat.add(parsed[2]);
			address.add(parsed[3]);
			rate.add(parsed[4]);*/
			int y = toParse.indexOf("#");
			for(int i = 0; i <= y; i++)
			{
				int j = 1;
				toParse = toParse.substring(j);
			}
		}

	}
	
	public void commentParse(String toParse)
	{
		while(!toParse.equals("") || toParse.length() > 0)
		{
			toParse = toParse.substring(1);
			toParse = toParse.substring(1);
			checkIfExist = false;
			String commentTemp;
			commentTemp = toParse.substring(0, toParse.indexOf("#"));
			//System.out.println(resTemp);
			for(int i = 0; i<comment.size(); i++)
			{
				if(comment.get(i).equals(commentTemp))
					checkIfExist = true;
			}
			if(!checkIfExist)
			{
				comment.add(commentTemp);
				System.out.println("comment = " + commentTemp);
			}
			int y = toParse.indexOf("#");
			
			for(int i = 0; i <= y; i++)
			{
				int j = 1;
				toParse = toParse.substring(j);
			}
		
		}
	}

}
