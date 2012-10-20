package org.ck.dt;

import java.util.ArrayList;

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
	public DecisionTreeNode buildDecisionTree(ArrayList<Sample> samples, ArrayList<String> featureList, int numDiscreteClassesList[])
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
		DecisionTreeNode new_test_node = new DecisionTreeNode(featureList.get(0), numDiscreteClassesList[0]);
		
		SampleSplitter sampleSplitter = new SampleSplitter(samples, featureList.get(0), numDiscreteClassesList[0]);
		sampleSplitter.splitSamples(); //Find an optimum value of the feature and Split the samples into left and right sample subsets 
		featureList.remove(0);
				
		//Creating the children nodes
		for(int i = 0; i < numDiscreteClassesList[0]; i++)
		{
			ArrayList<Sample> sampleSubset = sampleSplitter.getSampleSubset(i);
			new_test_node.setChildNode(i, buildDecisionTree(sampleSubset, (ArrayList<String>) featureList.clone(), numDiscreteClassesList));
			//new_test_node.setChildNode(i, buildDecisionTree(sampleSubset, featureList, numDiscreteClassesList));
		}
		
		return new_test_node;
	}

	/*
	 * Returns the class to which a majority of the samples belong
	 */
	private String getMajorityClass(ArrayList<Sample> samples) {
		int healthy = 0, colic = 0;
		for (Sample sample : samples)
		{
			if (sample.getClassification().equals("healthy.")) healthy++; else colic ++;
		}
		return healthy>colic ? "healthy":"colic";
	}

	/*
	 * Returns true if the majority class of the samples is greater than 0.9
	 */
	private boolean isStoppingCondition(ArrayList<Sample> samples) {
		int healthy = 0, colic = 0;
		for (Sample sample : samples)
		{
			if (sample.getClassification().equals("healthy.")) healthy++; else colic ++;
		}
		double prob_healthy = (double)healthy/samples.size();
		double prob_colic = 1 - prob_healthy;
		double max = (prob_healthy > 0.5)? prob_colic :prob_healthy;
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
