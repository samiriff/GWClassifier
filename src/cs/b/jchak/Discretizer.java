package cs.b.jchak;

import java.util.ArrayList;

public class Discretizer implements Constants
{		
	/* *************************************ALGORITHM 1 ******************************************/
	/*
	 * A naive discretizer that discretizes data based on the median, with those values
	 * 	below the median being set to 0 and those values above the median being set to 1.
	 */
	public static void discretizeBasedOnMedian(SampleCollection samples, int featureIndex)
	{
		//Median Calculation (without considering duplicates)
		ArrayList<Double> FeatureValueList = new ArrayList<Double>();
		for (Sample sample : samples.getSampleAsArrayList()) {
			double val = sample.getFeature(samples.getfeatureList().get(featureIndex)).getValue();
			//if (!FeatureValueList.contains(val))
			{
				FeatureValueList.add(val);
			}
		}
		
		//Converting the continuous values to discrete values
		ArrayList<Sample> samplesList = samples.getSampleAsArrayList();
		double median = FeatureValueList.get(FeatureValueList.size()/2);
		for(Sample sample : samplesList)
		{			
			double newValue = sample.getFeature(samples.getfeatureList().get(featureIndex)).getValue();
			if(newValue < median)
				newValue = 0.0;
			else
				newValue = 1.0;
			
			sample.setFeature(new Feature(samples.getfeatureList().get(featureIndex), newValue));
		}		
		
		//Setting the number of discrete classes for easy access during decision tree induction.
		samples.setNumDiscreteClasses(featureIndex, 2);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* *************************************ALGORITHM 2 ******************************************/
	
	private static double minValue = Double.MAX_VALUE;
	
	/*
	 * This is a static method that discretizes the values of a certain feature of a collection of samples.
	 * 		After discretization, the values can be any integer between 0 and binSize (inclusive)
	 * 		featureIndex specifies the index of the feature in the featureList array of the samples collection
	 */
	public static void discretizeEqualBinner(SampleCollection samples, int featureIndex, int binSize)
	{
		ArrayList<String> featureList = samples.getfeatureList();
		
		double delta = computeBinWidth(samples, featureList.get(featureIndex), binSize);
		System.out.println("Delta = " + delta);
		
		discretizeSamples(samples, featureList.get(featureIndex), delta);
		samples.setNumDiscreteClasses(featureIndex, binSize + 1);
	}
	
	/*
	 * Using the "Equal Width Interval Binning" algorithm for discretization.
	 * 
	 * See the paper for more information - http://robotics.stanford.edu/users/sahami/papers-dir/disc.pdf
	 */
	private static void discretizeSamples(SampleCollection samples, String featureName, double delta)
	{
		ArrayList<Sample> samplesList = samples.getSampleAsArrayList();
				
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
		ArrayList<Sample> samplesList = samples.getSampleAsArrayList();
		
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
