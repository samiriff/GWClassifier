package org.ck.sample;

import org.ck.gui.Constants;

public class DataHolder implements Constants{
	
	private static String TRAINING_SAMPLES_FILE_NAME;
	private static String TESTING_SAMPLES_FILE_NAME ;
	private static String ATTRIBUTES_FILE_NAME ;
	private static String POSITIVE_CLASS;
	private static String NEGATIVE_CLASS;

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
							break;
		
		case WHINE_DATASET:	TRAINING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.train";
							TESTING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.test";
							ATTRIBUTES_FILE_NAME = "Training Data/Whine/whine.attribute";
							POSITIVE_CLASS = "excellent.";
							NEGATIVE_CLASS = "poor.";
							
							break;
		default :		
		case WATER_DATASET: TRAINING_SAMPLES_FILE_NAME = "Training Data/Water/water.train";
							TESTING_SAMPLES_FILE_NAME = "Training Data/Water/water.test";
							ATTRIBUTES_FILE_NAME = "Training Data/Water/water.attribute";
							POSITIVE_CLASS = "portable.";
							NEGATIVE_CLASS = "not portable.";
							
		}
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

}
