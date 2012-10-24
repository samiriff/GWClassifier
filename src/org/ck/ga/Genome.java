package org.ck.ga;
import java.util.ArrayList;
import java.util.Random;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.gui.Constants;
import org.ck.gui.Constants.Filenames;
import org.ck.sample.DataHolder;
import org.ck.sample.SampleCollection;


public class Genome implements Constants
{
	static private SampleCollection samples;		//Sample Collection
	private static ArrayList<String> FeatureSuperSet;	//The complete set of features from which smaller subsets are derived for the GA
	private String chromosome;					//A bit string that shows which features are present or absent
	private double fitnessScore;				//Here, the fitness function is a function of the classification accuracy of the decision tree
	
	static
	{
		samples = new SampleCollection(DataHolder.getTrainingSamplesFileName(), DataHolder.getAttributesFileName());
		FeatureSuperSet = samples.getfeatureList();
		samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
	}
	
	/*
	 * This constructor takes a bit string representing features as a parameter and 
	 * 	initializes a decision tree from it.
	 */
	public Genome(String chromosome) throws OptimalScoreException
	{
		initDTfromChromosome(chromosome);
	}
	

	/*
	 * Initializes a decision tree that uses only the features present in the chromosome.
	 * 		It also calculates the fitness score of this chromosome.
	 */
	private void initDTfromChromosome(String chromosome) throws OptimalScoreException
	{
		this.chromosome = chromosome;
		
		DecisionTreeClassifier dtClassifier = getDecisionTree();
		dtClassifier.TestAndFindAccuracy();
		
		fitnessScore = dtClassifier.getAccuracy();		
		
		if(fitnessScore >= Constants.FITNESS_SCORE_THRESHOLD)
			throw new OptimalScoreException(this);
	}
	
	/*
	 * Returns the Fitness score of this genome
	 */
	public double getFitnessScore()
	{
		return fitnessScore;
	}
	
	/*
	 * Every bit of the chromosome string has a probability equal to mutationProbability of mutating.
	 * 		After mutation, the decision tree of this genome is reinitialized
	 */
	public void mutate(double mutationProbability) throws OptimalScoreException
	{
		StringBuffer chromosomeBuffer = new StringBuffer(getChromosome());
		for(int i=0; i<chromosomeBuffer.length(); i++)
		{
			if(getProbabilisticOutcome(mutationProbability))
			{				
				chromosomeBuffer.setCharAt(i, (chromosomeBuffer.charAt(i) == '0') ? '1':'0');
			}
		}
		
		
		initDTfromChromosome(chromosomeBuffer.toString());
	}
	
	/*
	 * Returns the chromosome
	 */
	public String getChromosome()
	{
		return chromosome;
	}

	/*
	 * Displays the chromosome as well as the Fitness score
	 */
	public void displayGenes()
	{
		System.out.println("Chromosome - "+chromosome+" FitnessValue - "+fitnessScore);
	}

	/*
	 * Generates an outcome for a random event.
	 */
	private boolean getProbabilisticOutcome(double probability)
	{
		Random rgen = new Random();
		return (rgen.nextInt((int)Math.pow(10, 6)) + 1 <= probability * Math.pow(10, 6));
	}
	
	/*
	 * Returns the number of features in the Feature Super set
	 */
	public static int getFeatureSuperSetSize()
	{
		return FeatureSuperSet.size();
	}
	
	/*
	 * Returns a new decision tree that was created by using only the features present in the chromosome.
	 */
	public DecisionTreeClassifier getDecisionTree()
	{
		ArrayList<String> features = new ArrayList<String>();
		
		for(int i=0; i<chromosome.length(); ++i)
			if(chromosome.charAt(i)=='1')
				features.add(FeatureSuperSet.get(i));
		
		DecisionTreeClassifier dtClassifier = new DecisionTreeClassifier(samples, features);
		SampleCollection training_samples = new SampleCollection(samples.getSamplesFilename(Filenames.TRAINING_SAMPLES_FILE), samples.getSamplesFilename(Filenames.FEATURES_FILE));
		training_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		dtClassifier.setTestingSamples(training_samples);
		
		return dtClassifier;
	}
}
