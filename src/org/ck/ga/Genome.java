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
	 * Used to reinitialize the static variables of this class, when DataHolder is updated, since static variables
	 * aren't updated automatically.
	 */
	public static void reInitializeStaticVariables()
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
		
		calculateFitnessScore(TRAINING_SET_WEIGHT, TEST_SET_WEIGHT);
		
		if(fitnessScore >= DataHolder.getFitnessScoreThreshold())
			throw new OptimalScoreException(this);
	}
	
	/*
	 * A redesigned Fitness Function calculator
	 * 	It takes into account the accuracy of the decision tree while classifying both, training and test examples
	 * 	The fitness score is a function of the weighted average of the two accuracies.
	 */
	private void calculateFitnessScore(double trainingWeight, double testingWeight)
	{
		DecisionTreeClassifier dtClassifier = getDecisionTree();
		dtClassifier.TestAndFindAccuracy();
		
		//Part 1 - Get training set accuracy
		double trainingSetAccuracy = dtClassifier.getAccuracy();	
		
		//Part 2 - Get test set accuracy
		SampleCollection test_samples = new SampleCollection(DataHolder.getTestingSamplesFileName(), DataHolder.getAttributesFileName());
		test_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);		
		dtClassifier.setTestingSamples(test_samples);		
		dtClassifier.TestAndFindAccuracy();		
		double testSetAccuracy = dtClassifier.getAccuracy();
		
		fitnessScore = (trainingWeight * trainingSetAccuracy + testingWeight * testSetAccuracy) / (trainingWeight + testingWeight);
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
		dtClassifier.setTrainingSamples(training_samples);
		
		return dtClassifier;
	}
	
	/*
	 * Returns the statically initialized Sample Collection
	 */
	public static SampleCollection getSamples()
	{
		return samples;
	}
}
