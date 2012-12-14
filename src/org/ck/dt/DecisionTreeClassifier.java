package org.ck.dt;

import java.util.ArrayList;

import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


/**
 * This class is used to construct a DT based Classifier that builds a DT by creating
 * a object of DecisionTreeBuilder class
 * 
 */
public class DecisionTreeClassifier {
	private DecisionTreeConstructor dtConstructor;
	private DecisionTreeNode RootNode;
	private SampleCollection trainingSamples;
	private SampleCollection testingSamples;
	private double Accuracy;
	
	
	/*
	 * This constructor takes an object of SampleCollection and initializes the DT
	 * using DTConstructor method
	 */
	public DecisionTreeClassifier(SampleCollection samples)
	{
		this.trainingSamples = samples;
		this.dtConstructor = new DecisionTreeConstructor(samples);
		this.RootNode = this.dtConstructor.getDecisionTreeRootNode();
	}
	
	
	/*
	 * This constructor takes an object of SampleCollection and initializes the DT
	 * using DTConstructor method
	 */
	public DecisionTreeClassifier(SampleCollection samples, ArrayList<String> features)
	{
		this.trainingSamples = samples;
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
	 * 
	 * Returns an arraylist of indices of all the samples that have been misclassified - This was added for the GUI
	 */
	public ArrayList<Integer> TestAndFindAccuracy()
	{
		ArrayList<Sample> samples = testingSamples.getSampleAsArrayList();
		int errors = 0;
		
		int index = 0;
		ArrayList<Integer> errorIndices = new ArrayList<Integer>();
		for(Sample sample : samples)
		{			
			String classifiedValue = Classify(sample);
			if (!classifiedValue.equals(sample.getClassification()))
			{
				//System.out.println("Classification Failed : " + "Actual Class is "+sample.getClassification());
				errorIndices.add(index);
				++errors;
			}
			index++;
		}
		Accuracy = 1 - (double)errors/samples.size();
		
		return errorIndices;
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
				return treeNode.getClassification();
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
	
	/*
	 * Returns the current training samples based on which this decision tree was constructed
	 */
	public SampleCollection getTrainingSamples()
	{
		return trainingSamples;
	}
	
	/*
	 * Returns the current testing samples based on which this decision tree was constructed
	 */
	public SampleCollection getTestingSamples()
	{
		return testingSamples;
	}
	
	/*
	 * Sets the samples based on which this decision tree will be constructed
	 */
	public void setTrainingSamples(SampleCollection samples)
	{
		trainingSamples = samples;
	}
	
	/*
	 * Takes a Tree SWT object and creates a graphical representation of the decision tree. This is a wrapper class
	 */
	public void getGraphicalDecisionTree(Tree tree)
	{		
		getGraphicalDecisionTree(tree, RootNode);
	}

	/*
	 * To reduce the number of lines of code, this method was made generic. Due to this, there is an 
	 * 		instanceof check to find the type-cast required wherever necessary.
	 */
	private <T> void getGraphicalDecisionTree(T treeItem, DecisionTreeNode root)
	{		
		if(root.isLeaf())
		{
			TreeItem item;
			if(treeItem instanceof Tree)
				item = new TreeItem((Tree) treeItem, 0);
			else
				item = new TreeItem((TreeItem)treeItem, 0);
			item.setText(root.getClassification());
		}
		else
		{
			for(int child = 0; child < root.getNumChildren(); child++)
			{
				TreeItem item;
				if(treeItem instanceof Tree)
					item = new TreeItem((Tree) treeItem, 0);
				else
					item = new TreeItem((TreeItem)treeItem, 0);				
				item.setText(root.getfeatureName() + " = " + child + "?");
				
				getGraphicalDecisionTree(item, root.getChildNode(child));
			}
		}
	}
}
