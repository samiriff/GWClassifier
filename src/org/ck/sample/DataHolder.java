package org.ck.sample;

import org.ck.gui.Constants;

public class DataHolder implements Constants{
	
	private static String TRAINING_SAMPLES_FILE_NAME;
	private static String TESTING_SAMPLES_FILE_NAME ;
	private static String ATTRIBUTES_FILE_NAME ;
	private static String SAVE_DATA_TO_FILE;
	private static String POSITIVE_CLASS;
	private static String NEGATIVE_CLASS;
	private static String CURRENT_DATASET;
	private static double FITNESS_SCORE_THRESHOLD;
	private static double CROSSOVER_PROBABILITY_THRESHOLD;
	private static double MUTATION_PROBABILITY_THRESHOLD;

	static
	{
		setDataset(DatasetOptions.HORSE_DATASET);
		setFitnessScoreThreshold(Constants.FITNESS_SCORE_THRESHOLD);
		setCrossoverProbabilityThreshold(Constants.CROSSOVER_PROBABILITY_THRESHOLD);
		setMutationProbabilityThreshold(Constants.MUTATION_PROBABILITY_THRESHOLD);
	}
	
	/**
	 * The option indicates the desired data sets to be used and
	 * depending on this option, the training sample and testing sample file
	 * are initialized.
	 */
	public DataHolder(DatasetOptions option)
	{
		setDataset(option);
	}
	
	/*
	 * This method sets the 
	 */
	public static void setDataset(DatasetOptions option)
	{
		switch(option)
		{
		case HORSE_DATASET: TRAINING_SAMPLES_FILE_NAME = "Training Data/Horse/horse.train";
							TESTING_SAMPLES_FILE_NAME = "Training Data/Horse/horse.test";
							ATTRIBUTES_FILE_NAME = "Training Data/Horse/horse.attribute";
							POSITIVE_CLASS = "healthy.";
							NEGATIVE_CLASS = "colic.";
							CURRENT_DATASET = "HORSE_DATASET";
							SAVE_DATA_TO_FILE = "Saved Data/HorseDT";
							break;
		
		case WHINE_DATASET:	TRAINING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.train";
							TESTING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.test";
							ATTRIBUTES_FILE_NAME = "Training Data/Whine/whine.attribute";
							POSITIVE_CLASS = "excellent.";
							NEGATIVE_CLASS = "poor.";
							CURRENT_DATASET = "WHINE_DATASET";
							SAVE_DATA_TO_FILE = "Saved Data/WhineDT";
							break;
		default :		
		case WATER_DATASET: TRAINING_SAMPLES_FILE_NAME = "Training Data/Water/water.train";
							TESTING_SAMPLES_FILE_NAME = "Training Data/Water/water.test";
							ATTRIBUTES_FILE_NAME = "Training Data/Water/water.attribute";
							POSITIVE_CLASS = "potable.";
							NEGATIVE_CLASS = "not potable.";
							CURRENT_DATASET = "WATER_DATASET";
							SAVE_DATA_TO_FILE = "Saved Data/WaterDT";
							
		}
	}
	public static String getSaveDatoToFileName()
	{
		return SAVE_DATA_TO_FILE;
	}
	
	public static String getTrainingSamplesFileName()
	{
		return TRAINING_SAMPLES_FILE_NAME;
	}
	public static String getTestingSamplesFileName()
	{
		return TESTING_SAMPLES_FILE_NAME;
	}
	public static String getAttributesFileName()
	{
		return ATTRIBUTES_FILE_NAME;
	}
	
	public static String getPositiveClass()
	{
		return POSITIVE_CLASS;
	}
	
	public static String getNegativeClass()
	{
		return NEGATIVE_CLASS;
	}

	public static void setFitnessScoreThreshold(double value)
	{
		FITNESS_SCORE_THRESHOLD = value;
	}
	
	public static double getFitnessScoreThreshold()
	{
		return FITNESS_SCORE_THRESHOLD;
	}
	
	public static void setCrossoverProbabilityThreshold(double value)
	{
		CROSSOVER_PROBABILITY_THRESHOLD = value;
	}
	
	public static double getCrossoverProbabilityThreshold()
	{
		return CROSSOVER_PROBABILITY_THRESHOLD;
	}
	
	public static void setMutationProbabilityThreshold(double value)
	{
		MUTATION_PROBABILITY_THRESHOLD = value;
	}
	
	public static double getMutationProbabilityThreshold()
	{
		return MUTATION_PROBABILITY_THRESHOLD;
	}

	public static String getCurrentDataSet() {

		return CURRENT_DATASET;
	}
}
