package cs.b.jchak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import cs.b.jchak.Feature;

/**
 * Sample class indicates each horse sample.
 * 
 * @author nsatvik
 *
 */
public class Sample {
	
	private HashMap<String, Feature> featureMap;		//Maps a feature name to its corresponding value
	private ArrayList<String> featureList;			//List of features to be tracked. It helps in retrieving feature values from the Hashmap in the proper order. 
	
	private String classifiedResult;
	
	/**
	 * Constructor that takes a string containing values for all the features, and a list of attributes for 
	 * initialization
	 * @param featureString - A comma-separated string of values for all the features
	 * @param attributeList - An ArrayList of names of attributes (featureNames)
	 */
	public Sample(String featureString, ArrayList<String> featureList)
	{		
		this.featureList = featureList;		
		int currentFeature = 0;
		featureMap = new HashMap<String, Feature>();
		
		StringTokenizer tokens = new StringTokenizer(featureString, ",");
		
		while(tokens.hasMoreTokens())
		{
			String token = tokens.nextToken();
			if(currentFeature < featureList.size())				
				featureMap.put(featureList.get(currentFeature), new Feature(featureList.get(currentFeature++), Double.parseDouble(token)));
			else
			{
				classifiedResult = token;
				currentFeature = 0;
			}
		}
	}
	
	/*
	 * This constructor just initializes the Feature Map and the attribute list from a parent Sample
	 * 			with a subset of attributes
	 */
	public Sample(Sample parentSample, ArrayList<String> subFeatureList)
	{
		featureMap = new HashMap<String, Feature>();
		this.featureList = subFeatureList;
		
		for(String feature : subFeatureList)
		{
			Feature attribute = parentSample.featureMap.get(feature);
			featureMap.put(feature, attribute);							
		}
		
		classifiedResult = parentSample.classifiedResult;
	}
	
	/*
	 * Returns a new Sample which contains values corresponding to the attributes in subAttributeList (subset)
	 */
	public Sample getSampleSubset(ArrayList<String> subFeatureList)
	{
		return new Sample(this, subFeatureList);
	}
	
		
	/*
	 * Displays all features of the sample included the classified Result
	 */
	public void display()
	{
		for(String feature : featureList)
		{
			if(featureMap.containsKey(feature))
				featureMap.get(feature).display();
		}
		
		System.out.print("Classification = " + classifiedResult);
	}

	/*
	 * Returns the value of the feature that corresponds to the feature name stored in the attribute parameter
	 * @param attribute - contains the name of the feature whose value is to be returned 
	 */
	public Feature getFeature(String feature)
	{
		if(featureMap.containsKey(feature))
			return featureMap.get(feature);
		return null;
	}
	
	/*
	 * Returns the class to which this sample belongs
	 */
	public String getClassification()
	{
		return classifiedResult;
	}
	
	
}
