package org.ck.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.ga.DTOptimizer;
import org.ck.ga.OptimalScoreException;
import org.ck.ga.Population;
import org.ck.sample.DataHolder;
import org.ck.sample.SampleCollection;

public class MainClass implements Constants{

	public static void main(String args[]) throws IOException
	{
		System.out.println("Hello, Welcome to the Decision Tree Based Classifier");
		
		//sampleCaller(); // This is for nsatvik
		sampleCaller2(); //This is for samiriff
		
		
	}
	
	
	public static void sampleCaller2() {
		//DataHolder.setDataset(DatasetOptions.HORSE_DATASET);
		Population population = null;
		try {
			population = new Population();
			population.displayPopulation();
			
			System.out.println("Starting Genetic Algorithm Engine...");
			System.out.println(DataHolder.getPositiveClass());
			System.out.println(DataHolder.getFitnessScoreThreshold());
			Thread.sleep(1000);
			population.runGeneticAlgorithm();
			
			
		} catch (OptimalScoreException e) {
			//e.printStackTrace();
			//population.displayPopulation();
		}
		//population.displayPopulation();
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sampleCaller()
	{
		DataHolder.setDataset(DatasetOptions.WHINE_DATASET);
		SampleCollection samples = new SampleCollection(DataHolder.getTrainingSamplesFileName(), DataHolder.getAttributesFileName());
		ArrayList<String> featureList = new ArrayList<String>();		//Subset of features
		featureList.add("PH");
		featureList.add("ALCOHOL");
		featureList.add("SULPHATES");
		featureList.add("CITRIC-ACID");
		featureList.add("VOLATILE-ACIDITY");
		//featureList.add("Breath rate");
		//featureList.add("Pulse rate");
		
		SampleCollection testing_samples = new SampleCollection(DataHolder.getTestingSamplesFileName(), DataHolder.getAttributesFileName());
		SampleCollection new_samples = new SampleCollection(samples, featureList);
		new_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		//Discretizing
		samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		testing_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);		
		
		new_samples.displaySamples();
	
		//DecisionTreeClassifier dtClassifier = new DecisionTreeClassifier(samples.getSampleCollectionSubset(featureList));
		DecisionTreeClassifier dtClassifier = new DecisionTreeClassifier(new_samples);
		
		System.out.println("\n\nTest Set Accuracy : ");
		dtClassifier.setTestingSamples(testing_samples);
		dtClassifier.TestAndFindAccuracy();
		dtClassifier.getAccuracy();
		
		System.out.println("Training Set Accuracy : ");
		dtClassifier.setTestingSamples(samples);
		dtClassifier.TestAndFindAccuracy();
		dtClassifier.getAccuracy();
		
	}
}
