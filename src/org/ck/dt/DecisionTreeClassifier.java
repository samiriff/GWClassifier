package org.ck.dt;

import java.util.ArrayList;

import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;


/**
 * This class is used to construct a DT based Classifier that builds a DT by creating
 * a object of DecisionTreeBuilder class
 * 
 */
public class DecisionTreeClassifier {
	private DecisionTreeConstructor dtConstructor;
	private DecisionTreeNode RootNode;
	private SampleCollection testingSamples;
	private double Accuracy;
	
	
	/*
	 * This constructor takes an object of SampleCollection and initializes the DT
	 * using DTConstructor method
	 */
	public DecisionTreeClassifier(SampleCollection samples)
	{
		this.dtConstructor = new DecisionTreeConstructor(samples);
		this.RootNode = this.dtConstructor.getDecisionTreeRootNode();
	}
	
	
	/*
	 * This constructor takes an object of SampleCollection and initializes the DT
	 * using DTConstructor method
	 */
	public DecisionTreeClassifier(SampleCollection samples, ArrayList<String> features)
	{
		this.dtConstructor = new DecisionTreeConstructor(samples,features);
		this.RootNode = this.dtConstructor.getDecisionTreeRootNode();
	}
	
	/*
	 * This method initializes the testingSamples variable
	 */
	public void setTestingSamples(SampleCollection test_Samples)
	{
		this.testingSamples = test_Samples;
	}
	
	/*
	 * This method uses the testingSamples and tests the accuracy of the 
	 * decisiontree and initializes the Accuracy variable.
	 */
	public void TestAndFindAccuracy()
	{
		ArrayList<Sample> samples = testingSamples.getSampleAsArrayList();
		int errors = 0;
		for(Sample sample : samples)
		{
			String classifiedValue = Classify(sample);
			if (!classifiedValue.equals(sample.getClassification()))
			{
				//System.out.println("Classification Failed : Actual Class is "+sample.getClassification());
				++errors;
			}
		}
		Accuracy = 1 - (double)errors/samples.size();
	}
	
	/*
	 * This method traverses the DT and Classifies the sample
	 */
	public String Classify(Sample sample)
	{
		DecisionTreeNode treeNode = RootNode;
		while(true)
		{
			if(treeNode.isLeaf())
			{
				return treeNode.getClassification()+"."; //This . is important!
			}
			
			String feature = treeNode.getfeatureName();			
			treeNode = treeNode.getChildNode((int)sample.getFeature(feature).getValue());
		}
	}
	/*
	 * Returns the accuracy of the DT constructed
	 */
	public double getAccuracy()
	{
		System.out.println("The Accuracy of the DT is "+Accuracy*100+"%");
		return Accuracy;
	}
	
}
