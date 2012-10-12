package cs.b.jchak;

public class DecisionTreeNode {
	private String FeatureName; //Name of the feature this node indicates
	private double lowerLimit;
	private double upperLimit;
	
	private boolean isLeaf; //Is this node a leaf
	private String ClassifiedResult;//If this is the leaf node what is the classified result
	
	private DecisionTreeNode Left, Right;//Left and Right Nodes

}
