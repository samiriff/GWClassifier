package cs.b.jchak;

/**
 * A Feature class to represent each feature of the sample like pH, CO3, NO3, NC, NH3 ...(indicated by the 
 * feature name), private variables to indicate it's lower and upper limit(I don't think these are needed for 
 * each instance, we'll redesign this), 
 * 
 *
 */
public class Feature {
	private String featureName; 	
	private double featureValue;
	
	/*
	 * Initializes the current feature with a Feature name and its corresponding value
	 */
	public Feature(String name, double value)
	{
		featureName = name;
		featureValue = value;
	}

	/*
	 * Displays the name and value of the feature
	 */
	public void display()
	{
		System.out.print(featureName + " = " + featureValue + "\t");
	}
	
	/*
	 * Returns the Name of the feature
	 */
	public String getName()
	{
		return featureName;
	}
	
	/*
	 * Returns the value of the feature
	 */
	public double getValue()
	{
		return featureValue;
	}
}
