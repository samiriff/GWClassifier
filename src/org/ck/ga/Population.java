package org.ck.ga;
import java.util.ArrayList;
import java.util.Random;

import org.ck.gui.Constants;


public class Population implements Constants
{
	private ArrayList<Genome> genomes;
	private Random rgen = new Random();
	
	public Population() throws OptimalScoreException
	{
		genomes = new ArrayList<Genome>();
		
		randomPopulationInit();		
	}
	
	public void runGeneticAlgorithm() throws OptimalScoreException
	{
		for(int i=0; i<NUM_OF_GENERATIONS; ++i)
			{
				double totalFitnessScore = assessFitness(genomes);
				System.out.println("Total Fitness Score = " + totalFitnessScore);
				naturalSelection(totalFitnessScore);
				//displayBestGenome();
			}
	}
	
	private void displayBestGenome() throws OptimalScoreException
	{
		double bestFitnessScore = 0;
		Genome bestGenome = null;
		for(int i=0; i<genomes.size(); i++)
		{
			if(genomes.get(i).getFitnessScore() > bestFitnessScore)
			{
				System.out.println("HAHAH");
				bestFitnessScore = genomes.get(i).getFitnessScore();
				bestGenome = genomes.get(i);
			}
		}
		
		System.out.println("Best Genome: ");
		//bestGenome.displayGenes();
		//throw new OptimalScoreException(bestGenome);
	}

	private void naturalSelection(double totalFitnessScore) throws OptimalScoreException
	{
		ArrayList<Genome> newPopulation = new ArrayList<Genome>();
		
		while(newPopulation.size() < genomes.size())
		{
			Genome randGenome1 = rouletteSelection(totalFitnessScore);
			Genome randGenome2 = rouletteSelection(totalFitnessScore);
			
			System.out.println("Selected 1 " + randGenome1.getFitnessScore());
			System.out.println("Selected 2 " + randGenome2.getFitnessScore());
			
			crossoverGenomes(randGenome1, randGenome2, newPopulation);
			mutateGenomes(newPopulation);
			
			//displayPopulation();
		}
		
		genomes = newPopulation;
	}
	
	private void mutateGenomes(ArrayList<Genome> newPopulation) throws OptimalScoreException
	{
		newPopulation.get(newPopulation.size() - 1).mutate(MUTATION_PROBABILITY);
		newPopulation.get(newPopulation.size() - 2).mutate(MUTATION_PROBABILITY);
	}
	
	private void crossoverGenomes(Genome father, Genome mother, ArrayList<Genome> newPopulation) throws OptimalScoreException
	{
		if(getProbabilisticOutcome(CROSSOVER_PROBABILITY))
		{
			int crossoverPoint = rgen.nextInt(father.getChromosome().length());
			
			Genome child1 = new Genome(father.getChromosome().substring(0, crossoverPoint) 
								+ mother.getChromosome().substring(crossoverPoint));
			Genome child2 = new Genome(mother.getChromosome().substring(0, crossoverPoint) 
					+ father.getChromosome().substring(crossoverPoint));
			
			System.out.println("Child 1");
			child1.displayGenes();
			System.out.println("Child 2");
			child2.displayGenes();
			
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
	
	private String toNBitBinaryString(int i, int n)
	{
		String str = Integer.toBinaryString(i);
		
		if(str.length() == n)
			return str;
		
		String zeroes = new String(new char[n - str.length()]).replace("\0", "0");
		return zeroes + str;

	}

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
	
	public void displayPopulation()
	{
		System.out.println("\nPopulation: ");
		for(int i=0; i<genomes.size(); i++)
			genomes.get(i).displayGenes();
		System.out.println();
	}

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
