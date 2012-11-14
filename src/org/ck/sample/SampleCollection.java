package org.ck.sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ck.dt.Discretizer;
import org.ck.gui.Constants;


/**
 * This class reads the sample data from a file and initializes all the necessary data structures to store
 * the data values for Classification analysis.
 */

public class SampleCollection implements Constants
{
	private ArrayList<Sample> samples;				
	private ArrayList<String> featureList;		
	private HashMap<String, Integer> featureNumDiscreteClasses;			//To keep track of the number of values of each attribute after discretization
	
	private class BinningVars
	{
		public double minValue;
		public double delta;
		
		public BinningVars(double d, double m)
		{
			delta = d;
			minValue = m;
		}
	}
	private ArrayList<BinningVars> binningVars;			//Used to keep track of the Equal Binning variables - delta and min of each feature
	
	
//	private String trainingSamplesFilename;								//Name of the file that has training examples
//	private String featuresFilename;									//Name of the file that has the list of attributes
	
	/*
	 * Constructor that takes as parameters, the file name of the file containing all the data samples,
	 * 		and the file name of the file that contains the list of features required to describe each
	 * 		data sample
	 */
	public SampleCollection(String samplesFileName, String featuresFileName)
	{
//		trainingSamplesFilename = samplesFileName; 
//		this.featuresFilename = featuresFileName;
		
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
		
		//featureNumDiscreteClasses = new int[featureList.size()];	
		featureNumDiscreteClasses = new HashMap<String, Integer>();
		
		binningVars = new ArrayList<SampleCollection.BinningVars>();
		for(int i=0; i<featureList.size(); i++)
			binningVars.add(null);
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
			int numDiscreteClasses = NUMBER_OF_BINS;
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
		featureNumDiscreteClasses.put(featureList.get(featureIndex), numValues);
	}
	
	/*
	 * Returns the number of discrete values for a given feature after discretization
	 */
	public int getNumDiscreteClasses(String feature)
	{
		return featureNumDiscreteClasses.get(feature);
	}
	
	/*
	 * Returns the array containing the number of discrete values for a given feature after discretization
	 */
	public HashMap<String, Integer> getNumDiscreteClassesList()
	{
		//return Arrays.copyOf(featureNumDiscreteClasses, featureNumDiscreteClasses.length);
		return (HashMap<String, Integer>) featureNumDiscreteClasses.clone();
	}
	
	/*
	 * Returns details of the filenames from which this collection read its samples.
	 */
	public String getSamplesFilename(Filenames type)
	{
		switch(type)
		{
		case TRAINING_SAMPLES_FILE: return DataHolder.getTrainingSamplesFileName();
		case FEATURES_FILE: return DataHolder.getAttributesFileName();
		default: return null;
		}		
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

	/*
	 * Adds a new entry (delta, minValue) to the arraylist of binningVars at index i
	 */
	public void addBinningVar(int index, double delta, double minValue)
	{
		binningVars.set(index, new BinningVars(delta, minValue));
	}
	
	/*
	 * Returns the delta value of the bin at index
	 */
	public double getBinningVarDelta(int index)
	{
		return binningVars.get(index).delta;
	}
	
	/*
	 * Returns the min value of the bin at index
	 */
	public double getBinningVarMinvalue(int index)
	{
		return binningVars.get(index).minValue;
	}
	
	/*
	 * Displays the bin values for each feature of the sample collection
	 */
	public void displayBinning()
	{
		for(int i=0; i<binningVars.size(); i++)
			System.out.println(binningVars.get(i).delta + "\t" + binningVars.get(i).minValue);
	}
	
	/*
	 * Discretizes a sample based on the Binning values of this sample collection
	 */
	public void discretizeSample(Sample sample)
	{
		for(int i=0; i<featureList.size(); i++)
			sample.discretize(featureList.get(i), binningVars.get(i).delta, binningVars.get(i).minValue);
	}
	
	public void discretizeSamplesBasedOnOtherSampleCollection(SampleCollection trainingSampleCollection)
	{
		for(Sample sample : samples)
		{
			trainingSampleCollection.discretizeSample(sample);
		}
	}
}
