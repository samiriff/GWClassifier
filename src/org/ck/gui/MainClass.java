package org.ck.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.ga.DTOptimizer;
import org.ck.ga.OptimalScoreException;
import org.ck.ga.Population;
import org.ck.sample.SampleCollection;

public class MainClass {

	public static void main(String args[]) throws IOException
	{
		System.out.println("Hello, Welcome to the Horse Classifier");
		
		//sampleCaller(); // This is for nsatvik
		sampleCaller2(); //This is for samiriff
	}
	private static void sampleCaller2() {
		Population population = null;
		try {
			population = new Population();
			population.displayPopulation();
			population.runGeneticAlgorithm();
			
			
		} catch (OptimalScoreException e) {
			e.printStackTrace();
			//population.displayPopulation();
		}
		population.displayPopulation();
	}
	private static void sampleCaller()
	{
		
		SampleCollection samples = new SampleCollection("Training Data/horse.train", "Training Data/horse.attribute");
		ArrayList<String> featureList = new ArrayList<String>();		//Subset of features
		featureList.add("K");
		featureList.add("Na");
		featureList.add("CL");
		featureList.add("HCO3");
		featureList.add("Endotoxin");
		featureList.add("Breath rate");
		//featureList.add("Pulse rate");
		SampleCollection testing_samples = new SampleCollection("Training Data/horse.test", "Training Data/horse.attribute");
		
		//Discretizing
		samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
		testing_samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);		
		
		samples.displaySamples();
	
		DecisionTreeClassifier dtClassifier = new DecisionTreeClassifier(samples.getSampleCollectionSubset(featureList));
		
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
