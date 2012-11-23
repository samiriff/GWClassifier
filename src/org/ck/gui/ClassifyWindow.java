package org.ck.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ck.dt.DecisionTreeClassifier;
import org.ck.gui.Constants.DatasetOptions;
import org.ck.sample.DataHolder;
import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

public class ClassifyWindow {
	private Shell shell;
	
	private int gridHorizontalSpacing = 10;
	private int gridVerticalSpacing = 4;
	private int gridMarginBottom = 5;
	private int gridMarginTop = 5;
	
	private Label []featureLabels;
	private Text []featureTextBox;
	private ArrayList<String> featureList;
	
	private SampleCollection samples;
	
	private DecisionTreeClassifier dtClassifier;
	private Tree graphicalDecisionTree = null;
	
	private Combo dataSetSelectorCombo;
	private Combo treeSelectorCombo;
	private Label ClassificationLabel;

	private Label infoLabel;
	
	public ClassifyWindow(Display display)
	{
		shell = new Shell(display);
		shell.setSize(840, 720);
		shell.setBackgroundImage(new Image(display, "Icons/white_background.png"));
		initUI();
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
	}
	
	private void initUI() {
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.horizontalSpacing = gridHorizontalSpacing;
		gridLayout.verticalSpacing = gridVerticalSpacing;
		gridLayout.marginBottom = gridMarginBottom;
		gridLayout.marginTop = gridMarginTop;
		shell.setLayout(gridLayout);
		shell.setText("Classifier Window");
		
		initDataLoaderPart();
		initFeatureReaderPart();
		initDTSelector();
		initGraphicalDecitionTree();
		ClassificationLabel = new Label(shell,SWT.BORDER);
		ClassificationLabel.setFont(new Font(shell.getDisplay(), "Helvectica", 20, SWT.ITALIC | SWT.BOLD));
		ClassificationLabel.setText("Classification Result\n Appears Here");
		
	}

	private void initButtons(Group featureReader) {
		Button ClassifyButton = new Button(featureReader,SWT.PUSH | SWT.CENTER);
		ClassifyButton.setText("Classify");
		Button ResetButton = new Button(featureReader,SWT.PUSH | SWT.CENTER);
		ResetButton.setText("Reset");
		ClassifyButton.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String featureLine = "";
				for(int i=0; i<featureList.size(); ++i)
				{
					featureLine += featureTextBox[i].getText()+",";
				}
				featureLine += DataHolder.getPositiveClass();//Ignore this, only for the format (Check the Sample constructor).
				Sample currentSample= new Sample(featureLine, featureList);
				String Classification = dtClassifier.Classify(currentSample);
				ClassificationLabel.setText("Classification\n" + Classification);
//				if(Classification.equals(DataHolder.getPositiveClass()))
//					ClassificationLabel.setBackground(new Color(shell.getDisplay(), 0, 1, 0));				
//				else
//					ClassificationLabel.setBackground(new Color(shell.getDisplay(), 1, 0, 0));
				
				
			}
		});
		ResetButton.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				for(int i=0; i<16; i++)
				{
					featureTextBox[i].setText("0");
				}
				
			}
		});
	}

	private void initGraphicalDecitionTree() {
		Tree previousTree = graphicalDecisionTree;
		
		graphicalDecisionTree = new Tree(shell, SWT.BORDER);
		dtClassifier.getGraphicalDecisionTree(graphicalDecisionTree);
		
		//graphicalDecisionTree.setLayoutData(gridData);
		//This block is executed if a previous table has to be overwritten with a new table
		if(previousTree != null)
		{
			graphicalDecisionTree.moveAbove(previousTree);
			previousTree.dispose();
			graphicalDecisionTree.getParent().layout();
		}
		
	}

	private void initFeatureReaderPart() {
		Group featureReader = new Group(shell, SWT.NONE);
		featureReader.setText("Enter Feature Values[0-"+Constants.NUMBER_OF_BINS+"]");
		GridLayout featureReaderLayout = new GridLayout(2,true);
		featureReaderLayout.marginWidth = 5;
		featureReaderLayout.marginHeight = 5;
		featureReader.setLayout(featureReaderLayout);
		
		featureLabels = new Label[16];
		featureTextBox = new Text[16];
		
		for(int i=0; i<16; ++i)
		{
			featureLabels[i] = new Label(featureReader, SWT.WRAP|SWT.BORDER);
			featureTextBox[i] = new Text(featureReader,SWT.BORDER);
		}
		initFeatureLabels();
		initButtons(featureReader);
	}

	private void initDataLoaderPart() {
		Group dataLoader = new Group(shell, SWT.NONE);
		dataLoader.setText("Load The Decision Tree");
		GridLayout dataLoaderLayout = new GridLayout(2,false);
		dataLoaderLayout.marginWidth = 5;
		dataLoaderLayout.marginHeight = 5;
		dataLoader.setLayout(dataLoaderLayout);
			
		Label dogName = new Label(dataLoader, SWT.NONE|SWT.CENTER);
		dogName.setText("Select Dataset");
		dataSetSelectorCombo = new Combo(dataLoader, SWT.DROP_DOWN);
        for(int i = 0; i < DatasetOptions.values().length; i++)
        	dataSetSelectorCombo.add("" + DatasetOptions.values()[i]);

        dataSetSelectorCombo.select(0);
        DataHolder.setDataset(DatasetOptions.valueOf(dataSetSelectorCombo.getText()));
        samples = new SampleCollection(DataHolder.getTrainingSamplesFileName(), DataHolder.getAttributesFileName());
    	samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
        dataSetSelectorCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	DataHolder.setDataset(DatasetOptions.valueOf(dataSetSelectorCombo.getText()));
            	initFeatureLabels();
            	initDTSelector();
            	samples = new SampleCollection(DataHolder.getTrainingSamplesFileName(), DataHolder.getAttributesFileName());
            	samples.discretizeSamples(Constants.DiscretizerAlgorithms.EQUAL_BINNING);
            	//Genome.reInitializeStaticVariables();
            }

			
        });
        infoLabel = new Label(shell,SWT.None | SWT.BORDER_SOLID);
        infoLabel.setText("General Information Label");
        Label savedValues = new Label(dataLoader, SWT.NONE|SWT.CENTER);
		savedValues.setText("Select Decision Tree");
		treeSelectorCombo = new Combo(dataLoader, SWT.DROP_DOWN);
		
		treeSelectorCombo.select(0);
		treeSelectorCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	initDecisionTree();
            };
        });
        
		
	}
	private void initDecisionTree() {
		String selection = treeSelectorCombo.getText();
    	String featureLine = selection.split("->")[0]; 
    	//System.out.println(featres[0]);
    	String features[] = featureLine.split(",");
    	ArrayList<String> featrList = new ArrayList<String>();
    	for(int i=0; i<features.length; ++i)
    		featrList.add(features[i]);
    	infoLabel.setText("Decision Tree with features\n"+featrList+"\nConstructed has a accuracy of "+selection.split("->")[1]);
    	
    	dtClassifier = new DecisionTreeClassifier(samples,featrList);
		initGraphicalDecitionTree();
	}
	private void initDTSelector()
	{
		treeSelectorCombo.removeAll();
		try {
			BufferedReader br = new BufferedReader(new FileReader(DataHolder.getSaveDatoToFileName()));
			while(true)
			{
				String line = br.readLine();
				if(line == null)
					break;
				treeSelectorCombo.add(line);
			}
			treeSelectorCombo.select(0);
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initDecisionTree();
	}
	private void initFeatureLabels() {
		featureList = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(DataHolder.getAttributesFileName()));
			int i = 0;
			while(true)
			{
				String line = br.readLine();
				if(line == null)
					break;
				featureList.add(line);
			}
			for(i=0;i<featureList.size();i++)
			{
				//if(i>=11)
				{
					//featureLabels[i].setVisible(true); //Some Bug here!
					//featureTextBox[i].setVisible(true);
				}
				featureLabels[i].setText(featureList.get(i));
				featureTextBox[i].setText("0");
				System.out.println(""+featureList.get(i));
				
			}
			for(;i<16;i++)
			{
				//featureLabels[i].setVisible(false);
				//featureTextBox[i].setVisible(false);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		new ClassifyWindow(new Display());
		
	}

}
