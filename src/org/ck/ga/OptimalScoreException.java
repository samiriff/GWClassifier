package org.ck.ga;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.gui.Constants;
import org.ck.sample.DataHolder;
import org.ck.sample.SampleCollection;

public class OptimalScoreException extends Exception implements Constants
{
	private Genome genome_solution;
	public OptimalScoreException()
	{}
	
	public OptimalScoreException(String msg) 
	{
	    super(msg);
	}

	/*
	 * Since this exception is thrown when the Genetic algorithm has found a genome that has a high fitness 
	 * score, this constructor finds out the accuracy of the chosen genome's decision tree on the test set.
	 */
	public OptimalScoreException(Genome genome) {
		this.genome_solution = genome;
		genome_solution.displayGenes();
		
		SampleCollection test_samples = new SampleCollection(DataHolder.getTestingSamplesFileName(), DataHolder.getAttributesFileName());
		test_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		
		DecisionTreeClassifier dtClassifier = genome_solution.getDecisionTree();
		dtClassifier.setTestingSamples(test_samples);
		
		dtClassifier.TestAndFindAccuracy();
		
		System.out.println("Test set accuracy = " + dtClassifier.getAccuracy());	
	}
}
