package org.ck.gui;

public interface Constants
{
	public static final String TRAINING_SAMPLES_FILE_NAME = "Training Data/horse.train";
	public static final String TESTING_SAMPLES_FILE_NAME = "Training Data/horse.test";
	public static final String ATTRIBUTES_FILE_NAME = "Training Data/horse.attribute";
	
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
	
	enum Filenames
	{
		TRAINING_SAMPLES_FILE,
		FEATURES_FILE
	}
	
	public static final int NUMBER_OF_BINS = 3;
	
	public static final double CROSSOVER_PROBABILITY = 0.85;
	public static final double MUTATION_PROBABILITY = 0.025;
	public static final int POPULATION_SIZE = 75;
	public static final int NUM_OF_GENERATIONS = 400;
	public static final double FITNESS_SCORE_THRESHOLD = 0.969;
}
