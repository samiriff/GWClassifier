package cs.b.jchak;

import java.util.ArrayList;

/*
 * Used to find an optimal way to split samples based on a given feature
 */
public class SampleSplitter {

	private ArrayList<Sample> samples;
	private String feature_name;
	
	private double Optimum_feature_value;
	private ArrayList<Sample> leftsampleSubset = new ArrayList<Sample>();
	private ArrayList<Sample> rightsampleSubset = new ArrayList<Sample>();
	
	/*
	 * This constructor initializes the class variables, and specifies that the list of samples should be
	 * 		split based on the parameter - feature.
	 */
	public SampleSplitter(ArrayList<Sample> samples, String feature)
	{
		this.samples = samples;
		this.feature_name = feature;		
	}

	/*
	 * Splits the given sample set into left and right samples based on the median of all values of the given
	 * 	feature. Duplicate feature values aren't considered. The median is stored in the variable
	 * 		Optimum_feature_value.
	 */
	public void splitSamples() {
		ArrayList<Double> FeatureValueList = new ArrayList<Double>();
		for (Sample sample : samples) {
			double val = sample.getFeature(feature_name).getValue();
			//if (!FeatureValueList.contains(val))
			{
				FeatureValueList.add(val);
			}
		}
		
		if(FeatureValueList.size() == 0)
			return;
		
		Optimum_feature_value = FeatureValueList.get(FeatureValueList.size()/2);
		
		for (Sample sample : samples )
		{
			if (sample.getFeature(feature_name).getValue() < Optimum_feature_value)
				leftsampleSubset.add(sample);
			else
				rightsampleSubset.add(sample);
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
}
