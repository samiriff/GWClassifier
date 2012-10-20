package org.ck.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ck.dt.Discretizer;
import org.ck.gui.Constants;
import org.ck.gui.Constants.DiscretizerAlgorithms;


/**
 * This class reads the sample data from a file and initializes all the necessary data structures to store
 * the data values for Classification analysis.
 */

public class SampleCollection implements Constants
{
	private ArrayList<Sample> samples;				
	private ArrayList<String> featureList;		
	private int featureNumDiscreteClasses[];			//To keep track of the number of values of each attribute after discretization
	
	/*
	 * Constructor that takes as parameters, the file name of the file containing all the data samples,
	 * 		and the file name of the file that contains the list of features required to describe each
	 * 		data sample
	 */
	public SampleCollection(String samplesFileName, String featuresFileName)
	{
		samples = new ArrayList<Sample>();		
		
		try {
			this.featureList = getfeatureList(featuresFileName);
			
			BufferedReader sampleFile = new BufferedReader(new FileReader(samplesFileName));			
			while(true)
			{
				String line = sampleFile.readLine();				
				if(line == null)
					break;				
				samples.add(new Sample(line, featureList));			
			}			
			sampleFile.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		featureNumDiscreteClasses = new int[featureList.size()];	
	}
	
	/*
	 * This constructor initializes a SampleCollection with features specified in subfeatureList,
	 * 	from a parent sample collection that has already been defined. (subset)
	 */
	public SampleCollection(SampleCollection parentSampleCollection, ArrayList<String> subfeatureList)
	{		
		featureList = subfeatureList;
		featureNumDiscreteClasses = parentSampleCollection.featureNumDiscreteClasses;
		
		ArrayList<Sample> samplesSubset = new ArrayList<Sample>();
		for(Sample sample : parentSampleCollection.samples)
		{
			Sample subSample = sample.getSampleSubset(subfeatureList);
			samplesSubset.add(subSample);
		}
		
		samples = samplesSubset;
	}
	
	/*
	 * Returns a new SampleCollection with features specified in subfeatureList,
	 * 	from a this sample collection as a parent table
	 */
	public SampleCollection getSampleCollectionSubset(ArrayList<String> subfeatureList)
	{
		return new SampleCollection(this, subfeatureList);
	}
	
	
	/*
	 * This method is used to call the desired method in the Discretizer class that will convert
	 * 		continuous valued features of the current sample collection to discrete-valued features.
	 * The algorithm for discretized is specified by the parameter, which can be any constant of the 
	 * 		enum - DiscretizerAlgorithms, defined in the Constants interface.
	 */
	public void discretizeSamples(DiscretizerAlgorithms algorithmType)
	{
		switch(algorithmType)
		{
		default:
			
		case MEDIAN:
			for(int i=0; i<featureList.size(); i++)
			{
				Discretizer.discretizeBasedOnMedian(this, i);
			}
			break;
			
		case EQUAL_BINNING:
			int numDiscreteClasses = 2;
			for(int i=0; i<featureList.size(); i++)
			{
				Discretizer.discretizeEqualBinner(this, i, numDiscreteClasses);
			}
			break;		
		}
	}
	
	/*
	 * Displays all the samples that have been stored by the program
	 */
	public void displaySamples()
	{
		for(Sample sample : samples)
		{
			sample.display();
			System.out.println();
		}
	}
	
	/*
	 * Returns the arraylist containing strings of features (features)
	 */
	public ArrayList<String> getfeatureList()
	{
		return featureList;
	}
	
	
	/*
	 * Displays the contents of the featureList arraylist
	 */
	public void displayfeatureList()
	{
		int i = 0;
		System.out.println("features: ");
		for(String feature : featureList)
			System.out.println(i++ + " " + feature);
	}
	/*
	 * This method returns the ArrayList of samples
	 */
	public ArrayList<Sample> getSampleAsArrayList()
	{
		return samples;
	}
	
	/*
	 * Sets the number of discrete values for a given feature after discretization
	 */
	public void setNumDiscreteClasses(int featureIndex, int numValues)
	{
		featureNumDiscreteClasses[featureIndex] = numValues;
	}
	
	/*
	 * Returns the number of discrete values for a given feature after discretization
	 */
	public int getNumDiscreteClasses(int featureIndex)
	{
		return featureNumDiscreteClasses[featureIndex];
	}
	
	/*
	 * Returns the array containing the number of discrete values for a given feature after discretization
	 */
	public int[] getNumDiscreteClassesList()
	{
		return featureNumDiscreteClasses;
	}
	
	/*
	 * Reads a file containing the list of features (feature names) necessary for describing each sample
	 */
	private static ArrayList<String> getfeatureList(String filename)throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		ArrayList<String> featureList = new ArrayList<String>();
		
		while(true)
		{
			String line = br.readLine();
			
			if(line == null)
				break;
			
			featureList.add(line);
		}
		
		return featureList;
	}

}
