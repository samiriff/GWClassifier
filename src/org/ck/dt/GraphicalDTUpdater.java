package org.ck.dt;

import org.eclipse.swt.widgets.Tree;

public class GraphicalDTUpdater extends Thread{
	Thread parent;
	DecisionTreeClassifier dtClassifier;
	Tree guiTree;
	public GraphicalDTUpdater(Thread parent, DecisionTreeClassifier dtClassifier, Tree graphicalDecisionTree)
	{
		this.parent = parent;
		this.dtClassifier = dtClassifier;
		this.guiTree = graphicalDecisionTree;
	}
	public void run()
	{
		dtClassifier.getGraphicalDecisionTree(guiTree);
		//dtClassifier.getAccuracy();
//		for(int i=0;i<10;i++)
//		{
//			System.out.println("Test "+i);
//		}
		parent.interrupt();
	}

}
