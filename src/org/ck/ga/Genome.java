package org.ck.ga;
import java.util.ArrayList;
import java.util.Random;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.gui.Constants;
import org.ck.sample.SampleCollection;


public class Genome
{
	static private SampleCollection samples;
	private static ArrayList<String> FeatureSuperSet;
	private String chromosome;
	private double fitnessScore;	
	
	static
	{
		samples = new SampleCollection("Training Data/horse.train", "Training Data/horse.attribute");
		FeatureSuperSet = samples.getfeatureList();
		samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
	}
	
	
	public Genome(String chromosome) throws OptimalScoreException
	{
		initDTfromChromosome(chromosome);
	}
	

	private void initDTfromChromosome(String chromosome) throws OptimalScoreException
	{
		this.chromosome = chromosome;
		ArrayList<String> features = new ArrayList<String>();
		for(int i=0; i<chromosome.length(); ++i)
			if(chromosome.charAt(i)=='1')
				features.add(FeatureSuperSet.get(i));
		DecisionTreeClassifier dtClassifier = new DecisionTreeClassifier(samples, features);
		SampleCollection test_samples = new SampleCollection("Training Data/horse.train", "Training Data/horse.attribute");
		test_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		dtClassifier.setTestingSamples(test_samples);
		dtClassifier.TestAndFindAccuracy();
		fitnessScore = dtClassifier.getAccuracy();		
		
		if(fitnessScore >= 0.96)
			throw new OptimalScoreException(this);
	}
	
	public double getFitnessScore()
	{
		return fitnessScore;
	}
	
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
	
	public String getChromosome()
	{
		return chromosome;
	}

	public void displayGenes()
	{
		System.out.println("Chromosome - "+chromosome+" FitnessValue - "+fitnessScore);
	}

	private boolean getProbabilisticOutcome(double probability)
	{
		Random rgen = new Random();
		return (rgen.nextInt((int)Math.pow(10, 6)) + 1 <= probability * Math.pow(10, 6));
	}
	public static int getFeatureSuperSetSize()
	{
		return FeatureSuperSet.size();
	}
}
