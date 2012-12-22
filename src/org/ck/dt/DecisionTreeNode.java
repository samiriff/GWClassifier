package org.ck.dt;

/**
 * This class is used to create objects that represent nodes in a BINARY decision tree
 */
public class DecisionTreeNode 
{
	private String featureName; //Name of the feature this node indicates
	
	private boolean isLeaf; //Is this node a leaf
	private String ClassifiedResult;//If this is the leaf node what is the classified result
	
	
	private DecisionTreeNode children[];
	
	/*
	 * This constructor Initializes the class variables with default values
	 * 		By default, this node is not a leaf and contains no children.
	 */
	public DecisionTreeNode()
	{
		isLeaf = false;
		children = null;
	}
	
		
	/*
	 * This constructor initializes the feature name of the (internal) node
	 */
	public DecisionTreeNode(String feature_name)
	{
		this.featureName = feature_name;
	}
	
	/*
	 * This constructor initializes the feature name and the number of children of the (internal) node.
	 */
	public DecisionTreeNode(String feature_name, int numChildren)
	{
		this(feature_name);
		children = new DecisionTreeNode[numChildren];
	}
	
	/*
	 * Converts the current node to a leaf node
	 */
	public void setAsLeaf()
	{
		isLeaf = true;
	}
	
	/*
	 * Returns true if this node is a leaf node
	 */
	public boolean isLeaf()
	{
		return isLeaf;
	}
	

	/*
	 * Can be used to modify the node's feature name
	 */
	public void setfeatureName(String feature_name)
	{
		featureName = feature_name;
	}
	
	/*
	 * Returns the node's feature name
	 */
	public String getfeatureName()
	{
		return featureName;
	}
		
	/*
	 * Returns the child node at index
	 */
	public DecisionTreeNode getChildNode(int index)
	{ 
		return children[index];
	}
	
	/*
	 * Initializes the current node's child at index
	 */
	public void setChildNode(int index, DecisionTreeNode node)
	{
		children[index] = node;
	}
	
	/*
	 * Sets the classification of a leaf node
	 */
	public void setClassifiedResult(String Class)
	{
		this.ClassifiedResult = Class;
	}
	
	/*
	 * Returns the classification of a leaf node
	 */
	public String getClassification() 
	{
		return ClassifiedResult;
	}

	/*
	 * Returns the number of children of the given node
	 */
	public int getNumChildren()
	{
		if(children != null)
			return children.length;
		return 0;
	}
}
