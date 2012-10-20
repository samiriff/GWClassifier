package org.ck.gui;

public interface Constants
{
	enum Category
	{
		VERY_LOW,
		LOW,
		MEDIUM,
		HIGH,
		VERY_HIGH
	}
	
	enum DiscretizerAlgorithms
	{
		MEDIAN,
		EQUAL_BINNING
	}
	public static final double CROSSOVER_PROBABILITY = 0.85;
	public static final double MUTATION_PROBABILITY = 0.025;
	public static final int POPULATION_SIZE = 75;
	public static final int NUM_OF_GENERATIONS = 400;
}
