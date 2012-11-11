package org.ck.gui;

import java.util.ArrayList;

import org.ck.ga.Genome;
import org.ck.ga.OptimalScoreException;
import org.ck.gui.Constants.DatasetOptions;
import org.ck.sample.DataHolder;
import org.ck.sample.Sample;
import org.ck.sample.SampleCollection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MainWindow implements Constants
{
	private Shell shell;
	private Display display;
	
	private int gridHorizontalSpacing = 4;
	private int gridVerticalSpacing = 4;
	private int gridMarginBottom = 5;
	private int gridMarginTop = 5;
	
	private Slider fitnessSlider;
	private Label fitnessSliderLabel;
	private Button runButton;
	
	private Slider crossoverSlider;
	private Label crossoverSliderLabel;
	
	private Slider mutationSlider;
	private Label mutationSliderLabel;
	
	private Table samplesTable = null;
	private Button discretizeCheckBox;
		
	private StyledText accuracyTextArea;
	
	
	
	
	private Color red, blue, gray, white;
    
    
    
    
    
	
	/*
	 * A constructor that takes in a display parameter and initializes the shell and other components of the UI
	 */
	public MainWindow(Display display)
	{
		this.display = display;
		
		red = display.getSystemColor(SWT.COLOR_RED);
	    blue = display.getSystemColor(SWT.COLOR_BLUE);
	    white = display.getSystemColor(SWT.COLOR_WHITE);
	    gray = display.getSystemColor(SWT.COLOR_GRAY);
		
		shell = new Shell(display);
		shell.setText("Decision Tree Classifier");
		
		centerShell();
		initUI();
		
		shell.setSize(500, 700);
		
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}		
	}
	
	/*
	 * The Main method
	 */
	public static void main(String args[])
	{
		Display display = new Display();
		new WelcomeWindow(display);
		display.dispose();
	}
	
	/*
	 * Centers the shell on the screen.... Doesn't work
	 */
	private void centerShell() 
	{		
		Rectangle bds = shell.getDisplay().getBounds();
		
		Point p = shell.getSize();
		
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	
	/*
	 * Initializes the UI
	 */
	private void initUI()
	{	
		//Initialize Grid Layout parameters
		GridLayout gridLayout = new GridLayout(4, true);
		gridLayout.horizontalSpacing = gridHorizontalSpacing;
		gridLayout.verticalSpacing = gridVerticalSpacing;
		gridLayout.marginBottom = gridMarginBottom;
		gridLayout.marginTop = gridMarginTop;
		shell.setLayout(gridLayout);
		
		//Adding Widgets
		addLabel("THE ULTIMATE CLASSIFIER", gridHorizontalSpacing, SWT.CENTER);
		addDataSamplesComboBox();
		addBreak(4);
		
		addListBox();
		addBreak(4);
		
		fitnessSlider = addFitnessThresholdSlider();
		fitnessSlider.setVisible(false);
		
		crossoverSlider = addCrossoverRateSlider();
		crossoverSlider.setVisible(false);
		
		mutationSlider = addMutationRateSlider();
		mutationSlider.setVisible(false);
				
		addBreak(4);
		addBreak(1);
		runButton = addRunButton();	
		runButton.setVisible(false);
		
		addBreak(2);
		addSamplesTable(false);
		addBreak(1);
		addDiscretizeCheckbox();		
		
		addResultDisplay();
	}


	/*
	 * Adds a run button, to start Machine Learning 
	 */
	private Button addRunButton()
	{
		Button button =  new Button(shell, SWT.PUSH);
		button.setText("Run the Engine");
		
		addToGrid(button, 2);		
		
		button.addSelectionListener(new SelectionAdapter() {
		  @Override
		  public void widgetSelected(SelectionEvent e) {
		    // Handle the selection event
			  try
			  {		
				  MainClass.sampleCaller2();
			  }
			  catch(OptimalScoreException exception)
			  {						  
				  try{Thread.sleep(1000);} catch (InterruptedException e1){}
				  accuracyTextArea.setVisible(true);
				  
				  String result = "Training Set Accuracy = " + exception.getTrainingSetAccuracy() + "\n";
				  result += "Test Set Accuracy = " + exception.getTestSetAccuracy() + "\n";
				  result += "Selected Features = " + exception.getSelectedFeatures(); 
				  accuracyTextArea.setText(result);				  
				  
				  
				  ArrayList<Integer> trainingErrorIndices = exception.getTrainingErrorIndices();
				  for(int index : trainingErrorIndices)
				  {					  
					  samplesTable.getItem(index).setForeground(red);
				  }				  
			  }
		  }
		});			
		
		return button;
	}

	/*
	 * This method can be used to insert 'num' blank labels to create spaces in the Grid Layout
	 */
	private void addBreak(int num)
	{
		final Label label = new Label(shell, SWT.LEFT);
	    label.setText("");
	
        GridData gridData = new GridData();
	    gridData.horizontalSpan = num;
	    gridData.horizontalAlignment = GridData.FILL;
	    label.setLayoutData(gridData);	
	}

	/*
	 * Adds a combo box to select the name of the data samples file
	 */
	private void addDataSamplesComboBox()
	{
		addLabel("Select Data Samples File: ", 2, SWT.CENTER);

        final Combo combo = new Combo(shell, SWT.DROP_DOWN);
        for(int i = 0; i < DatasetOptions.values().length; i++)
        	combo.add("" + DatasetOptions.values()[i]);

        combo.select(0);
        combo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	DataHolder.setDataset(DatasetOptions.valueOf(combo.getText()));
            	Genome.reInitializeStaticVariables();
            	addSamplesTable(discretizeCheckBox.getSelection());
            	
            	accuracyTextArea.setText("");
            };
        });

        addToGrid(combo, 2);
	}

	/*
	 * Adds a slider to vary the fitness threshold for the fitness function of the genetic algorithm
	 * 	Returns the initialized slider
	 */
	private Slider addFitnessThresholdSlider()
	{        
		final double sliderRange = 1000;
		
		fitnessSliderLabel = addLabel("Fitness Threshold --->  ", 2, SWT.CENTER);
		fitnessSliderLabel.setVisible(false);
		
		final Slider slider = new Slider(shell, SWT.HORIZONTAL);
		slider.setMaximum((int)sliderRange);
		slider.setSelection((int) (DataHolder.getFitnessScoreThreshold() * sliderRange));
		addToGrid(slider, 2);
		
		fitnessSliderLabel.setText(fitnessSliderLabel.getText() + slider.getSelection() / sliderRange);
		
		slider.addListener (SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {		
		        double value = slider.getSelection() / sliderRange;
		        fitnessSliderLabel.setText("Fitness Threshold --->  " + value);
		        DataHolder.setFitnessScoreThreshold(value);
		        
		        accuracyTextArea.setText("");
		    }
		});
		
		slider.setVisible(false);
		
		return slider;
	}
	
	/*
	 * Adds a slider to vary the Crossover Rate for the natural selection process of the genetic algorithm
	 * 	Returns the initialized slider
	 */
	private Slider addCrossoverRateSlider()
	{        
		final double sliderRange = 10000;
		
		crossoverSliderLabel = addLabel("Crossover Rate --->  ", 2, SWT.CENTER);
		crossoverSliderLabel.setVisible(false);
		
		final Slider slider = new Slider(shell, SWT.HORIZONTAL);
		slider.setMaximum((int)sliderRange);
		slider.setSelection((int) (DataHolder.getCrossoverProbabilityThreshold() * sliderRange));
		addToGrid(slider, 2);
		
		crossoverSliderLabel.setText(crossoverSliderLabel.getText() + slider.getSelection() / sliderRange);
		
		slider.addListener (SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {		
		        double value = slider.getSelection() / sliderRange;
		        crossoverSliderLabel.setText("Crossove Rate --->  " + value);
		        DataHolder.setCrossoverProbabilityThreshold(value);
		        
		        accuracyTextArea.setText("");
		    }
		});
		
		slider.setVisible(false);
		
		return slider;
	}
	
	/*
	 * Adds a slider to vary the Mutation Rate for Genome Mutation of the genetic algorithm
	 * 	Returns the initialized slider
	 */
	private Slider addMutationRateSlider()
	{        
		final double sliderRange = 10000;
		
		mutationSliderLabel = addLabel("Mutation Rate --->  ", 2, SWT.CENTER);
		mutationSliderLabel.setVisible(false);
		
		final Slider slider = new Slider(shell, SWT.HORIZONTAL);
		slider.setMaximum((int)sliderRange);
		slider.setSelection((int) (DataHolder.getMutationProbabilityThreshold() * sliderRange));
		addToGrid(slider, 2);
		
		mutationSliderLabel.setText(mutationSliderLabel.getText() + slider.getSelection() / sliderRange);
		
		slider.addListener (SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {		
		        double value = slider.getSelection() / sliderRange;
		        mutationSliderLabel.setText("Mutation Rate --->  " + value);
		        DataHolder.setMutationProbabilityThreshold(value);
		        
		        accuracyTextArea.setText("");
		    }
		});
		
		slider.setVisible(false);
		
		return slider;
	}
	
	/*
	 * Adds a list box to select an appropriate algorithm for Machine Learning.
	 * 		The UI changes based on the algorithm selected
	 */
	private void addListBox()
	{       
        addLabel("Select Algorithm", 2, SWT.CENTER);
        
        final List list = new List(shell, SWT.BORDER);        
        list.add("Decision Tree Classifier");
        //list.add("K-Means Clustering Classifier");
        //list.select(0);

        list.addListener(SWT.Selection, new Listener () {
            public void handleEvent (Event e) {
                String[] items = list.getSelection();
                
                if(items[0].equalsIgnoreCase("Decision Tree Classifier"))
                {
	            	fitnessSliderLabel.setVisible(true);
	            	fitnessSlider.setVisible(true);
	            	crossoverSliderLabel.setVisible(true);
	            	crossoverSlider.setVisible(true);
	            	mutationSliderLabel.setVisible(true);
	            	mutationSlider.setVisible(true);
	            	runButton.setVisible(true);
	            	
	            	accuracyTextArea.setText("");
                }
                else
                {
                	fitnessSliderLabel.setVisible(false);
	            	fitnessSlider.setVisible(false);
	            	crossoverSliderLabel.setVisible(false);
	            	crossoverSlider.setVisible(false);
	            	mutationSliderLabel.setVisible(false);
	            	mutationSlider.setVisible(false);
	            	runButton.setVisible(false);
	            	
	            	accuracyTextArea.setText("");
                }
            }
        });

        addToGrid(list, 2);     
	}
	
	private void addDiscretizeCheckbox()
	{
		 discretizeCheckBox = new Button(shell, SWT.CHECK);
	     discretizeCheckBox.setText("Show Discretized Values");
	     discretizeCheckBox.setSelection(false);
	     
	     addToGrid(discretizeCheckBox, 2);

	     discretizeCheckBox.addSelectionListener(new SelectionAdapter()
	     {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (discretizeCheckBox.getSelection()) {
                    addSamplesTable(true);
                } else {
                    addSamplesTable(false);
                }
            }
        });
	}
	
	/*
	 * This method creates an Excel-type table to display all the samples of the selected dataset
	 */
	private void addSamplesTable(boolean isDiscretize)
	{
		Table tempTable = samplesTable;					//Required for replacement in Grid Layout
		
		samplesTable = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		samplesTable.setLinesVisible (true);
		samplesTable.setHeaderVisible (true);
		
		GridData data = new GridData(); //SWT.FILL, SWT.FILL, false, false);
		data.heightHint = 200;
		data.widthHint = 200;
		data.horizontalSpan = 4;
	    data.horizontalAlignment = GridData.FILL;
		samplesTable.setLayoutData(data);
		
		//This block is executed if a previous table has to be overwritten with a new table
		if(tempTable != null)
		{
			samplesTable.moveAbove(tempTable);
			tempTable.dispose();
			samplesTable.getParent().layout();
		}
		
		SampleCollection samplesCollection = null;
		if(isDiscretize)
			samplesCollection = Genome.getSamples();
		else
			samplesCollection = new SampleCollection(DataHolder.getTrainingSamplesFileName(), DataHolder.getAttributesFileName());
		
		ArrayList<String> featureList = samplesCollection.getfeatureList();
		for (String feature : featureList) 
		{
			TableColumn column = new TableColumn(samplesTable, SWT.NONE);
			column.setText(feature);
		}	
		
		//For the last column - classification
		TableColumn column = new TableColumn(samplesTable, SWT.NONE);
		column.setText("Class");
		
		ArrayList<Sample> samplesList = samplesCollection.getSampleAsArrayList();
		
		for(Sample sample : samplesList)
		{
			//sample.display();
			//System.out.println();
			TableItem item = new TableItem (samplesTable, SWT.NONE);
			
			int featureIndex = 0;
			for(String feature : featureList)
				item.setText(featureIndex++, "" + sample.getFeature(feature).getValue());
			item.setText(featureIndex, sample.getClassification());
		}
		
		for (int i=0; i <  featureList.size() + 1; i++) 
		{
			samplesTable.getColumn(i).pack();
		}
	}
	
	/*
	 * Creates a text area where the results of the classification process can be displayed
	 */
	private void addResultDisplay()
	{
		accuracyTextArea = new StyledText (shell, SWT.BORDER);
		accuracyTextArea.setVisible(false);
				
		GridData gridData = new GridData();
	    gridData.horizontalSpan = 4;
	    gridData.heightHint = 100;
	    gridData.horizontalAlignment = GridData.FILL;
	    (accuracyTextArea).setLayoutData(gridData);
	}
	
	
	/*
	 * This method creates a label widget with the lyrics as the text parameter, and size = horizontalSpacing
	 * 		and style as parameter.
	 * 		Returns the Initialized label
	 */
	private Label addLabel(String lyrics, int horizontalSpacing, int style)
	{	
		Label label = new Label(shell, style);  
        label.setText(lyrics);        
        addToGrid(label, horizontalSpacing);
        return label;
	}
	
	/*
	 * A Generic Method to attach a generic widget to the grid, initialized with horizontal span
	 */
	private <T> void addToGrid(T widget, int horizantalSpan)
	{
		GridData gridData = new GridData();
	    gridData.horizontalSpan = horizantalSpan;
	    gridData.horizontalAlignment = GridData.FILL;
	    ((Control) widget).setLayoutData(gridData);
	}
}
