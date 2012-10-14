package cs.b.jchak;

import java.io.IOException;
import java.util.ArrayList;

public class MainClass {
	
	public static void main(String args[])throws IOException
	{
		System.out.println("Hello, Welcome to the Ground Water Classifier");
		
		//Creating a parent table from file data
		SampleCollection samples = new SampleCollection("Training Data/horse.train", "Training Data/AttributeList");
		//samples.displaySamples();		
		
		//Example code to get tables from parent table with a subset of features
		
		//Creating the subset of features
		ArrayList<String> featureList = new ArrayList<String>();		//Subset of features
		featureList.add("CL");		
		featureList.add("HCO3");
			
		SampleCollection subSamples = samples.getSampleCollectionSubset(featureList);
		subSamples.displaySamples();		
	}
	
	

}
