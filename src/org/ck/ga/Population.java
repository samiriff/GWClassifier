package org.ck.ga;
import java.util.ArrayList;
import java.util.Random;

import org.ck.gui.Constants;
import org.ck.sample.DataHolder;


public class Population implements Constants
{
	private ArrayList<Genome> genomes;
	private Random rgen = new Random();
	
	/*
	 * Initializes the genomes list with a random population
	 */
	public Population() throws OptimalScoreException
	{
		genomes = new ArrayList<Genome>();
		
		randomPopulationInit();		
	}
	
	/*
	 * Darwin's Survival of the Fittest algorithm
	 */
	public void runGeneticAlgorithm() throws OptimalScoreException
	{
		for(int i=0; i<NUM_OF_GENERATIONS; ++i)
			{
				double totalFitnessScore = assessFitness(genomes);
				System.out.println("Total Fitness Score = " + totalFitnessScore);
				naturalSelection(totalFitnessScore);
				//displayBestGenome();
			}
		displayBestGenome();
	}
	
	private void displayBestGenome() throws OptimalScoreException
	{
		double bestFitnessScore = 0;
		Genome bestGenome = null;
		for(int i=0; i<genomes.size(); i++)
		{
			if(genomes.get(i).getFitnessScore() > bestFitnessScore)
			{
				bestFitnessScore = genomes.get(i).getFitnessScore();
				bestGenome = genomes.get(i);
			}
		}
		
		System.out.println("Best Genome: ");
		//bestGenome.displayGenes();
		//throw new OptimalScoreException(bestGenome);
	}

	/*
	 * Creates a new population from the old population by selecting two genomes randomly at a time, and
	 * 		performing crossover and mutation operations.
	 */
	private void naturalSelection(double totalFitnessScore) throws OptimalScoreException
	{
		ArrayList<Genome> newPopulation = new ArrayList<Genome>();
		
		while(newPopulation.size() < genomes.size())
		{
			Genome randGenome1 = rouletteSelection(totalFitnessScore);
			Genome randGenome2 = rouletteSelection(totalFitnessScore);
			
			//System.out.println("Selected 1 " + randGenome1.getFitnessScore());
			//System.out.println("Selected 2 " + randGenome2.getFitnessScore());
			
			crossoverGenomes(randGenome1, randGenome2, newPopulation);
			mutateGenomes(newPopulation);
			
			//displayPopulation();
		}
		
		genomes = newPopulation;
	}
	
	/*
	 * Performs genetic mutation on the two most recent offspring 
	 */
	private void mutateGenomes(ArrayList<Genome> newPopulation) throws OptimalScoreException
	{
		newPopulation.get(newPopulation.size() - 1).mutate(DataHolder.getMutationProbabilityThreshold());
		newPopulation.get(newPopulation.size() - 2).mutate(DataHolder.getMutationProbabilityThreshold());
	}
	
	/*
	 * With a probability equal to CROSSOVER_PROBABILITY, two new children are created by mixing the traits of
	 * 		two genomes based on a crossover point. These new children are added to the new population. The parents aren't.
	 * With a probability equal to (1 - CROSSOVER_PROBABILITY), the father and mother are added to the new population.
	 */
	private void crossoverGenomes(Genome father, Genome mother, ArrayList<Genome> newPopulation) throws OptimalScoreException
	{
		if(getProbabilisticOutcome(DataHolder.getCrossoverProbabilityThreshold()))
		{
			int crossoverPoint = rgen.nextInt(father.getChromosome().length());
			
			Genome child1 = new Genome(father.getChromosome().substring(0, crossoverPoint) 
								+ mother.getChromosome().substring(crossoverPoint));
			Genome child2 = new Genome(mother.getChromosome().substring(0, crossoverPoint) 
					+ father.getChromosome().substring(crossoverPoint));
			
			/*System.out.println("Child 1");
			child1.displayGenes();
			System.out.println("Child 2");
			child2.displayGenes();*/
			
			//New Generation
			newPopulation.add(child1);
			newPopulation.add(child2);
			
			return;
		}
		else
		{
			newPopulation.add(father);
			newPopulation.add(mother);
		}		
	}
	
	/*
	 * Converts a number i of any length to a bit string of length n 
	 */
	private String toNBitBinaryString(int i, int n)
	{
		String str = Integer.toBinaryString(i);
		
		if(str.length() == n)
			return str;
		
		String zeroes = new String(new char[n - str.length()]).replace("\0", "0");
		return zeroes + str;

	}

	/*
	 * This kind of selection ensures that genomes having a higher fitness score than others have a better
	 * 		chance of being seleted for reproduction.
	 */
	private Genome rouletteSelection(double totalFitnessScore)
	{
	    double ball  = rgen.nextDouble() * totalFitnessScore;
	    double slice = 0.0;
	 
	    for(int i=0; i<genomes.size(); i++)
	    {
	        slice += genomes.get(i).getFitnessScore();
	 
	        if(ball < slice)
	            return genomes.get(i);
	    }
	    
	    return genomes.get(0);
	}
	
	/*
	 * Returns the sum of all fitness scores of all the genomes in the current population.
	 */
	private double assessFitness(ArrayList<Genome> genomes)
	{
		double totalFitnessScore = 0.0;
		for(int i=0; i<genomes.size(); i++)
		{
			double fitnessScore = genomes.get(i).getFitnessScore();
			totalFitnessScore += fitnessScore;
		}
		
		return totalFitnessScore;
	}
	
	/*
	 * Displays the population
	 */
	public void displayPopulation()
	{
		System.out.println("\nPopulation: ");
		for(int i=0; i<genomes.size(); i++)
			genomes.get(i).displayGenes();
		System.out.println();
	}

	/*
	 * Initializes a random population
	 */
	private void randomPopulationInit() throws OptimalScoreException
	{
		int numOfFeatures = Genome.getFeatureSuperSetSize();
		double upperLimit = Math.pow(2, numOfFeatures)-1;
		int[] FeatureSubsetValues = new int[(int)upperLimit];
		for(int i=0; i<upperLimit; ++i)
			FeatureSubsetValues[i] = i;
		//randomShuffle(FeatureSubsetValues);
		for(int i=0; i< POPULATION_SIZE; i++)
			genomes.add(new Genome(toNBitBinaryString(FeatureSubsetValues[i],numOfFeatures)));		
	}
	
	private void randomShuffle(int[] featureSubsetValues) {
		for(int i=0; i<featureSubsetValues.length; ++i)
		{
			int j = rgen.nextInt(featureSubsetValues.length);
			int temp = featureSubsetValues[0];
			featureSubsetValues[0] = featureSubsetValues[j];
			featureSubsetValues[j] = temp;
		}
	}

	
	private boolean getProbabilisticOutcome(double probability)
	{
		return (rgen.nextInt((int)Math.pow(10, 6)) + 1 <= probability * Math.pow(10, 6));
	}
	
}
