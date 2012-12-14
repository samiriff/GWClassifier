package org.ck.sample;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Used to find an optimal way to split samples based on a given feature
 */
public class SampleSplitter {

	private ArrayList<Sample> samples;
	private String feature_name;
	
	private double Optimum_feature_value;
	private ArrayList<Sample> leftsampleSubset = new ArrayList<Sample>();	//Not needed now
	private ArrayList<Sample> rightsampleSubset = new ArrayList<Sample>();	//Not needed now
	
	private ArrayList<Sample> sampleSubsets[];		//An array of arraylists
	
	/*
	 * This constructor initializes the class variables, and specifies that the list of samples should be
	 * 		split based on the parameter - feature.
	 */
	public SampleSplitter(ArrayList<Sample> samples, String feature, int numDiscreteClasses)
	{
		this.samples = samples;
		this.feature_name = feature;	
		
		//Initializing the Array of the arraylists of samples, that will contain the split subsets for a multiway decision tree
		sampleSubsets = (ArrayList<Sample>[])new ArrayList[numDiscreteClasses];
		for(int i=0; i<sampleSubsets.length; i++)
			sampleSubsets[i] = new ArrayList<Sample>();
	}

	/*
	 * Splits the given sample set into left and right samples based on the median of all values of the given
	 * 	feature. Duplicate feature values aren't considered. The median is stored in the variable
	 * 		Optimum_feature_value.
	 */
	public void splitSamples() {
				
		for (Sample sample : samples )
		{			
			sampleSubsets[(int)sample.getFeature(feature_name).getValue()].add(sample);
		}		
	}

	/*
	 * Returns the value based on which the data is split into left and right subsets.
	 */
	public double getOptimumValue() 
	{		
		return Optimum_feature_value;
	}

	/*
	 * Returns the samples for which the given feature has values lesser than the Optimum value.
	 */
	public ArrayList<Sample> getLeftSampleSubset() 
	{
		return leftsampleSubset;
	}

	/*
	 * Returns the samples for which the given feature has values greater than the Optimum value.
	 */
	public ArrayList<Sample> getRightSampleSubset() 
	{
		return rightsampleSubset;
	}	
	
	/*
	 * Returns the sample subset of the "index"th partition
	 */
	public ArrayList<Sample> getSampleSubset(int index)
	{
		return sampleSubsets[index];
	}
	
	/*
	 * Returns the Information Gain of the current split, calculated using the formula:
	 * 		Entropy of Parent Table - Sum(k/n * Entropy of subsetTable i)
	 */
	public double getInformationGain()
	{
		double informationGain = 0.0;
		
		for(int i=0; i<sampleSubsets.length; i++)
		{
			informationGain += ((double)sampleSubsets[i].size() / (double)samples.size()) * getEntropy(sampleSubsets[i]);
		}

		informationGain = getEntropy(samples) - informationGain;
		
		return informationGain;
	}
	
	/*
	 * Returns the entropy of the given sample list, calculated by the formula 
	 * 		sum ( - p ln(p) )
	 */
	private double getEntropy(ArrayList<Sample> samples)
	{
		HashMap<String, Double> groups = new HashMap<String, Double>();
		
		//Find number of samples for each classification
		for(Sample sample : samples)
		{
			String classification = sample.getClassification();
			if(groups.containsKey(classification))
				groups.put(classification, groups.get(classification) + 1);
			else
				groups.put(classification, 1.0);
		}
		
		double entropy = 0.0;
		for(String key : groups.keySet())
		{			
			double probability = groups.get(key) / samples.size();
			entropy += - (probability * Math.log(probability) / Math.log(2)); 
		}
		
		return entropy;		
	}
}
