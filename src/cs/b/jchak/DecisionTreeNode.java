package cs.b.jchak;

public class DecisionTreeNode {
	private String FeatureName; //Name of the feature this node indicates
	private double lowerLimit;
	private double upperLimit;
	
	private boolean isLeaf; //Is this node a leaf
	private String ClassifiedResult;//If this is the leaf node what is the classified result
	
	private DecisionTreeNode Left, Right;//Left and Right Nodes
	
	public DecisionTreeNode()
	{
		Left = Right = null;
		lowerLimit = upperLimit = 0;
		isLeaf = false;
	}
	public DecisionTreeNode(String feature_name)
	{
		this.FeatureName = feature_name;
	}
	public void setAsLeaf()
	{
		isLeaf = true;
	}
	public boolean isLeaf()
	{
		return isLeaf;
	}
	public void setLowerLimit(double low_value)
	{
		lowerLimit = low_value;
	}
	
	public void setUpperLimit(double high_value)
	{
		upperLimit = high_value;
	}
	public void setFeatureName(String feature_name)
	{
		FeatureName = feature_name;
	}
	public String getFeatureName()
	{
		return FeatureName;
	}
	
	public void setLeftNode(DecisionTreeNode left)
	{
		this.Left = left;
	}
	public void setRightNode(DecisionTreeNode right)
	{
		this.Right = right;
	}
	public void setClassifiedResult(String Class)
	{
		this.ClassifiedResult = Class;
	}
	public String getClassification() {
		return ClassifiedResult;
	}

}
