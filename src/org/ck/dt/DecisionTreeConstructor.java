package org.ck.dt;

import java.util.ArrayList;
import java.util.HashMap;

import org.ck.sample.DataHolder;
import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;
import org.ck.sample.SampleSplitter;

/**
 * This class will take the training data as input and build a DT
 * and return the RootNode	
 */
public class DecisionTreeConstructor
{	
	private DecisionTreeNode RootNode;
	private static final double MAX_PROBABILITY_STOPPING_CONDITION = 0.98;		//Required by isStoppingCondition()

	/*
	 * This constructor takes as a parameter - a collection of samples and constructs a MULTIWAY decision tree
	 */
	public DecisionTreeConstructor(SampleCollection samples) 
	{
		RootNode = buildDecisionTree(samples.getSampleAsArrayList(), samples.getfeatureList(), samples.getNumDiscreteClassesList());
	}
	
	/*
	 * This constructor takes as a parameter - a collection of samples, a subset of features constructs a MULTIWAY decision tree
	 * considering only those parameters in features.
	 */
	public DecisionTreeConstructor(SampleCollection samples, ArrayList<String> features) 
	{
		RootNode = buildDecisionTree(samples.getSampleAsArrayList(), features, samples.getNumDiscreteClassesList());
	}
	
	
	
	/*
	 * Takes as parameters - an arraylist of samples and an arraylist of features
	 * 		Constructs a  multiway decision tree recursively, and returns the root of the decision tree.
	 * 		Makes use of the SampleSplitter class methods
	 */
	public DecisionTreeNode buildDecisionTree(ArrayList<Sample> samples, ArrayList<String> featureList, HashMap<String, Integer> numDiscreteClassesList)
	{
		//System.out.println("buildDecisionTree - "+samples.size()+"\t"+featureList+" "+featureList.size());
		
		//Base Condition
		if ((samples.size() > 0 && isStoppingCondition(samples)) || (featureList.size()==0))
		{

			DecisionTreeNode newleaf = new DecisionTreeNode();
			newleaf.setAsLeaf();
			
			newleaf.setClassifiedResult(getMajorityClass(samples));
			//System.out.println("New leaf Node - The classification is "+ newleaf.getClassification());
			return newleaf;
		}

		/*
		 * Find a node for feature(0) and it's optimum value for splitting and initialize the node
		 * split into left and right sample array lists, then call recursively buildDecisionTree for left and right
		 * return node
		 */
		int bestFeatureIndex = findBestSplitFeatureIndex(samples, featureList, numDiscreteClassesList);
		
		DecisionTreeNode new_test_node = new DecisionTreeNode(featureList.get(bestFeatureIndex), numDiscreteClassesList.get(featureList.get(bestFeatureIndex)));
		
		SampleSplitter sampleSplitter = new SampleSplitter(samples, featureList.get(bestFeatureIndex), numDiscreteClassesList.get(featureList.get(bestFeatureIndex)));
		sampleSplitter.splitSamples(); //Find an optimum value of the feature and Split the samples into left and right sample subsets
		
		String featureName = featureList.get(bestFeatureIndex);
		featureList.remove(bestFeatureIndex);
				
		//Creating the children nodes
		for(int i = 0; i < numDiscreteClassesList.get(featureName); i++)
		{
			ArrayList<Sample> sampleSubset = sampleSplitter.getSampleSubset(i);
			new_test_node.setChildNode(i, buildDecisionTree(sampleSubset, (ArrayList<String>) featureList.clone(), numDiscreteClassesList));
		}
		
		return new_test_node;
	}
	
	/*
	 * This method tries to split the samples based on every feature in featureList.
	 * 		It returns the index of the feature in featureList which has the highest information gain.
	 */
	private int findBestSplitFeatureIndex(ArrayList<Sample> samples, ArrayList<String> featureList, HashMap<String, Integer> numDiscreteClassesList)
	{
		double maxInformationGain = Double.MIN_VALUE;
		int bestFeatureIndex = 0;
		
		int index = 0;
		for(String feature : featureList)
		{
			SampleSplitter sampleSplitter = new SampleSplitter(samples, feature, numDiscreteClassesList.get(feature));
			sampleSplitter.splitSamples(); //Find an optimum value of the feature and Split the samples into left and right sample subsets
			
			//System.out.println(sampleSplitter.getInformationGain());
			
			if(sampleSplitter.getInformationGain() > maxInformationGain)
			{
				maxInformationGain = sampleSplitter.getInformationGain();
				bestFeatureIndex = index; 
			}
			
			index++;
		}
		
		//System.out.println("Best = " + bestFeatureIndex + "  " + featureList.get(bestFeatureIndex));
		return bestFeatureIndex;
	}

	/*
	 * Returns the class to which a majority of the samples belong
	 */
	private String getMajorityClass(ArrayList<Sample> samples) {
		int positive_class = 0, negative_class = 0;
		for (Sample sample : samples)
		{
			if (sample.getClassification().equals(DataHolder.getPositiveClass())) positive_class++; else negative_class++;
		}
		return positive_class>negative_class ? DataHolder.getPositiveClass():DataHolder.getNegativeClass();
	}

	/*
	 * Returns true if the majority class of the samples is greater than 0.9
	 */
	private boolean isStoppingCondition(ArrayList<Sample> samples) {
		int positive = 0;
		for (Sample sample : samples)
		{
			if (sample.getClassification().equals(DataHolder.getPositiveClass())) positive++;
		}
		double prob_positive = (double)positive/samples.size();
		double prob_negative = 1 - prob_positive;
		double max = (prob_positive > 0.5)? prob_positive :prob_negative;
		return (max > MAX_PROBABILITY_STOPPING_CONDITION);
	}
	
	/*
	 * This method returns the RootNode of the DT
	 */
	public DecisionTreeNode getDecisionTreeRootNode()
	{
		return RootNode;
	}
}
