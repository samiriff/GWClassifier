package cs.b.jchak;

import java.util.ArrayList;

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
}
