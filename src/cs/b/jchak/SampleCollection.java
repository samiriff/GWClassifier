package cs.b.jchak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class reads the sample data from a file and initializes all the necessary data structures to store
 * the data values for Jinkchak analysis.
 */

public class SampleCollection
{
	private ArrayList<Sample> samples;
	
	/*
	 * Constructor that takes as parameters, the file name of the file containing all the data samples,
	 * 		and the file name of the file that contains the list of attributes required to describe each
	 * 		data sample
	 */
	public SampleCollection(String samplesFileName, String attributesFileName)
	{
		samples = new ArrayList<Sample>();		
		
		try {
			ArrayList<String> attributeList = getAttributeList(attributesFileName);
			
			BufferedReader sampleFile = new BufferedReader(new FileReader(samplesFileName));
			
			while(true)
			{
				String line = sampleFile.readLine();
				
				if(line == null)
					break;
				
				samples.add(new Sample(line, attributeList));			
			}
			
			sampleFile.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Displays all the samples that have been stored by the program
	 */
	public void displaySamples()
	{
		for(Sample sample : samples)
		{
			sample.display();
			System.out.println();
		}
	}
	
	/*
	 * Reads a file containing the list of attributes (feature names) necessary for describing each sample
	 */
	private static ArrayList<String> getAttributeList(String filename)throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		ArrayList<String> attributeList = new ArrayList<String>();
		
		while(true)
		{
			String line = br.readLine();
			
			if(line == null)
				break;
			
			attributeList.add(line);
		}
		
		return attributeList;
	}
}
