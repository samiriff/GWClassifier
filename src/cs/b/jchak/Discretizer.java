package cs.b.jchak;

import java.util.ArrayList;

public class Discretizer implements Constants
{	
	private static double minValue = Double.MAX_VALUE;
	
	/*
	 * This is a static method that discretizes the values of a certain feature of a collection of samples.
	 * 		After discretization, the values can be any integer between 0 and binSize - 1 (inclusive)
	 * 		featureIndex specifies the index of the feature in the featureList array of the samples collection
	 */
	public static void discretizeEqualBinner(SampleCollection samples, int featureIndex, int binSize)
	{
		ArrayList<String> featureList = samples.getfeatureList();
		
		double delta = computeBinWidth(samples, featureList.get(featureIndex), binSize);
		System.out.println("Delta = " + delta);
		
		//Algorithm 1
		discretizeSamples(samples, featureList.get(featureIndex), delta);
		
		//Implement more algorithms and choose the best one.
	}
	
	/*
	 * Using the "Equal Width Interval Binning" algorithm for discretization.
	 * 
	 * See the paper for more information - http://robotics.stanford.edu/users/sahami/papers-dir/disc.pdf
	 */
	private static void discretizeSamples(SampleCollection samples, String featureName, double delta)
	{
		ArrayList<Sample> samplesList = samples.getSampleList();
				
		for(Sample sample : samplesList)
		{
			double newValue = (int)((sample.getFeature(featureName).getValue() - minValue) / delta);
			sample.setFeature(new Feature(featureName, newValue));
		}
	}
	
	/*
	 * Computes delta = (xmax - xmin) / k
	 */
	private static double computeBinWidth(SampleCollection samples, String featureName, int binSize)
	{
		ArrayList<Sample> samplesList = samples.getSampleList();
		
		minValue = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		
		for(Sample sample : samplesList)
		{
			Feature feature = sample.getFeature(featureName);
			if(feature.getValue() < minValue)
				minValue = feature.getValue();
			if(feature.getValue() > max)
				max = feature.getValue();
		}
		
		double delta = (max - minValue) / binSize;		
		return delta;
	}
}
