package org.ck.ga;

import java.util.ArrayList;

import javax.print.attribute.standard.Chromaticity;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.gui.Constants;
import org.ck.sample.DataHolder;
import org.ck.sample.SampleCollection;

public class OptimalScoreException extends Exception implements Constants
{
	private Genome genome_solution;
	
	private double trainingSetAccuracy;
	private double testSetAccuracy;
	
	private ArrayList<Integer> trainingErrorIndices;
	private ArrayList<Integer> testErrorIndices;
	
	private DecisionTreeClassifier dtClassifier;
	
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
		
		System.out.println("\n\nEXCEPTION CAUGHT - SOLUTION FOUND");
		//System.out.println(genome.samples.getSamplesFilename(Filenames.TRAINING_SAMPLES_FILE));
		
		DecisionTreeClassifier dtClassifier = genome_solution.getDecisionTree();
		trainingErrorIndices = dtClassifier.TestAndFindAccuracy();
		System.out.println("Training Set Accuracy = " + (trainingSetAccuracy = dtClassifier.getAccuracy()));
		
		SampleCollection test_samples = new SampleCollection(DataHolder.getTestingSamplesFileName(), DataHolder.getAttributesFileName());
		test_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);		
		dtClassifier.setTestingSamples(test_samples);		
		testErrorIndices = dtClassifier.TestAndFindAccuracy();
		
		System.out.println("Test set accuracy = " + (testSetAccuracy = dtClassifier.getAccuracy()));	
		
		//test_samples.displayBinning();
		this.dtClassifier = dtClassifier;
	}
	
	public double getTrainingSetAccuracy()
	{
		return trainingSetAccuracy;
	}
	
	public double getTestSetAccuracy()
	{
		return testSetAccuracy;
	}
	
	public ArrayList<Integer> getTrainingErrorIndices()
	{
		return trainingErrorIndices;
	}
	
	public ArrayList<Integer> getTestErrorIndices()
	{
		return testErrorIndices;
	}
	
	public ArrayList<String> getSelectedFeatures()
	{
		ArrayList<String> selectedFeatures = new ArrayList<String>();
			
		String chromosome = genome_solution.getChromosome();
		ArrayList<String> featureList = genome_solution.getSamples().getfeatureList();
		System.out.println(chromosome);
		for(int i=0; i<chromosome.length(); i++)
			if(chromosome.charAt(i) == '1')
				selectedFeatures.add(featureList.get(i));
		
		System.out.println(selectedFeatures);
		return selectedFeatures;
	}
	
	public DecisionTreeClassifier getCurrentDTClassifier()
	{
		return dtClassifier;
	}
}
