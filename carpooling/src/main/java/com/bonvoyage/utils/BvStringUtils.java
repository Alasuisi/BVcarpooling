package com.bonvoyage.utils;

import org.apache.commons.lang3.StringUtils;

public class BvStringUtils {
	private static String blueTag="<font color=#2C82EE>";
	private static String greenTag = "<font color=#38DD7C>";
	private static String redTag = "<font color=#EE4F83>";
	private static String greyTag = "<font color=#BDB7BC>";
	private static String closeTag = "</font>";
public static String bvColorizeWord(String input)
	{
	
	String split[]= StringUtils.split(input);
	String result=new String();
	for(int i=0;i<split.length;i++)
		{
		if(split[i].length()>=5)
			{
			int jump=split[i].length()/3;
			int position=0;
			int color=0;
			while(position<split[i].length())
				{
				if(position%jump==0)
					{
					if(color==0)
						{
						result=result+blueTag+split[i].substring(position, position+1)+closeTag;
						//System.out.println("caso1");
						//color++;
						}
					if(color==1)
						{
						result=result+greenTag+split[i].substring(position, position+1)+closeTag;
						//System.out.println("caso2");
						//color++;
						}
					if(color==2)
						{
						result=result+redTag+split[i].substring(position, position+1)+closeTag;
						//System.out.println("caso3");
						//color++;
						}
					color++;
					if(color==3)color=0;
					position++;
					}else
						{
						result=result+greyTag+split[i].substring(position, position+1)+closeTag;
						position++;
						}
				
				}
			}else
				{
				result = result+greyTag+split[i]+closeTag+" ";
				}
		result=result+" ";
		}
	//System.out.println(result);
	return result;
	}
public static String bvColorizeString(String input)
	{
		String result = new String();
		if(input.length()>=5)
		{
		int jump=input.length()/3;
		int position=0;
		int color=0;
		while(position<input.length())
			{
			if(position%jump==0)
				{
				if(color==0)
					{
					result=result+blueTag+input .substring(position, position+1)+closeTag;
					//System.out.println("caso1");
					//color++;
					}
				if(color==1)
					{
					result=result+greenTag+input.substring(position, position+1)+closeTag;
					//System.out.println("caso2");
					//color++;
					}
				if(color==2)
					{
					result=result+redTag+input.substring(position, position+1)+closeTag;
					//System.out.println("caso3");
					//color++;
					}
				color++;
				if(color==3)color=0;
				position++;
				}else
					{
					result=result+greyTag+input.substring(position, position+1)+closeTag;
					position++;
					}
			
			}
		}else
			{
			result = result+greyTag+input+closeTag+" ";
			}
	//result=result+" ";
		return result;
	}
}
