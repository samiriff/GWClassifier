package cs.b.jchak;

import java.io.IOException;
import java.util.ArrayList;

public class MainClass {

	public static void main(String args[]) throws IOException
	{
		System.out.println("Hello, Welcome to the Ground Water Classifier");

		sampleCaller(); // This is for nsatvik
		//sampleCaller2(); //This is for samiriff
	}
	private static void sampleCaller2() {
		//Creating a parent table from file data
		SampleCollection samples = new SampleCollection("Training Data/horse.train", "Training Data/AttributeList");
		//samples.displaySamples();		

		//Example code to get tables from parent table with a subset of features

		//Creating the subset of features
		ArrayList<String> featureList = new ArrayList<String>();		//Subset of features
		featureList.add("CL");		
		featureList.add("HCO3");

		SampleCollection subSamples = samples.getSampleCollectionSubset(featureList);
		//subSamples.displaySamples();	
		
		
		//Discretizer Example
		for(int i=0; i<samples.getfeatureList().size(); i++)
			Discretizer.discretizeEqualBinner(samples, i, 5);
		samples.displaySamples();

	}
	private static void sampleCaller()
	{
		SampleCollection samples = new SampleCollection("Training Data/horse.train", "Training Data/AttributeList");
		ArrayList<String> featureList = new ArrayList<String>();		//Subset of features
		featureList.add("K");
		featureList.add("Na");
		featureList.add("CL");	
		featureList.add("HCO3");		
		featureList.add("Endotoxin");
		featureList.add("Breath rate");
		featureList.add("Pulse rate");
		
		
		//This will construct a decision tree using the above features only				
		DecisionTreeConstructor dtbuilder = new DecisionTreeConstructor(samples.getSampleCollectionSubset(featureList));
	}
}
