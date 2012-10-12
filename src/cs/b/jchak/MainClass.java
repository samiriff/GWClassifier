package cs.b.jchak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainClass {
	
	public static void main(String args[])throws IOException
	{
		System.out.println("Hello, Welcome to the Ground Water Classifier");
		
		SampleCollection samples = new SampleCollection("Training Data/horse.train", "Training Data/AttributeList");
		samples.displaySamples();		
		
		
		//Read fr and print out :P
		
	}
	
	

}
