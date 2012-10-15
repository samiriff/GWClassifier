package cs.b.jchak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads the sample data from a file and initializes all the necessary data structures to store
 * the data values for Classification analysis.
 */

public class SampleCollection
{
	private ArrayList<Sample> samples;				
	private ArrayList<String> featureList;		
	
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
	}
	
	/*
	 * This constructor initializes a SampleCollection with features specified in subfeatureList,
	 * 	from a parent sample collection that has already been defined. (subset)
	 */
	public SampleCollection(SampleCollection parentSampleCollection, ArrayList<String> subfeatureList)
	{		
		featureList = subfeatureList;
		
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
	
	public ArrayList<Sample> getSampleAsArrayList()
	{
		return samples;
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
