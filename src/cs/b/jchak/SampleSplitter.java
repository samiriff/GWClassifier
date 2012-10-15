package cs.b.jchak;

import java.util.ArrayList;

public class SampleSplitter {

	private ArrayList<Sample> samples;
	private String feature_name;
	
	private double Optimum_feature_value;
	private ArrayList<Sample> leftsampleSubset = new ArrayList<Sample>();
	private ArrayList<Sample> rightsampleSubset = new ArrayList<Sample>();
	
	
	public SampleSplitter(ArrayList<Sample> samples, String feature)
	{
		this.samples = samples;
		this.feature_name = feature;		
	}

	public void splitSamples() {
		ArrayList<Double> FeatureValueList = new ArrayList<Double>();
		for (Sample sample : samples) {
			double val = sample.getFeature(feature_name).getValue();
			if (!FeatureValueList.contains(val))
			{
				FeatureValueList.add(val);
			}
		}
		Optimum_feature_value = FeatureValueList.get(FeatureValueList.size()/2);
		
		for (Sample sample : samples )
		{
			if (sample.getFeature(feature_name).getValue() < Optimum_feature_value)
				leftsampleSubset.add(sample);
			else
				rightsampleSubset.add(sample);
		}
		
	}

	public double getOptimumValue() {
		
		return Optimum_feature_value;
	}

	public ArrayList<Sample> getLeftSampleSubset() {
		return leftsampleSubset;
	}

	public ArrayList<Sample> getRightSampleSubset() {
		return rightsampleSubset;
	}	
}
