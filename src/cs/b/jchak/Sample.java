package cs.b.jchak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import cs.b.jchak.Feature;

/**
 * Sample class indicates each horse sample.
 * 
 * @author nsatvik
 *
 */
public class Sample {
	private int year;
	private String Location;
	private ArrayList<Feature> features;
	
	private String classifiedResult;
	
	/**
	 * Constructor that takes a string containing values for all the features, and a list of attributes for 
	 * initialization
	 * @param featureString - A comma-separated string of values for all the features
	 * @param attributeList - An ArrayList of names of attributes (featureNames)
	 */
	public Sample(String featureString, ArrayList<String> attributeList)
	{		
		int currentAttribute = 0;
		features = new ArrayList<Feature>();
		
		StringTokenizer tokens = new StringTokenizer(featureString, ",");
		
		while(tokens.hasMoreTokens())
		{
			String token = tokens.nextToken();
			if(currentAttribute < attributeList.size() - 1)				
				features.add(new Feature(attributeList.get(currentAttribute++), Double.parseDouble(token)));
			else
			{
				classifiedResult = tokens.nextToken();
				currentAttribute = 0;
			}
		}
	}
	
	/*
	 * Displays all features of the sample included the classified Result
	 */
	public void display()
	{
		for(Feature feature : features)
		{
			feature.display();
			System.out.print(", ");
		}
		
		System.out.print("Classification = " + classifiedResult);
	}

}
