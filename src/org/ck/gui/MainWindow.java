package org.ck.gui;

import org.ck.gui.Constants.DatasetOptions;
import org.ck.sample.DataHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

public class MainWindow
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
	
	/*
	 * A constructor that takes in a display parameter and initializes the shell and other components of the UI
	 */
	public MainWindow(Display display)
	{
		this.display = display;
		
		shell = new Shell(display);
		shell.setText("Decision Tree Classifier");
		
		centerShell();
		initUI();
		
		shell.setSize(500, 500);
		
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
		new MainWindow(display);
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
		
		addBreak(4);
		addBreak(1);
		runButton = addRunButton();	
		runButton.setVisible(false);
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
		    MainClass.sampleCaller2();
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
		fitnessSliderLabel = addLabel("Fitness Threshold --->  ", 2, SWT.CENTER);
		fitnessSliderLabel.setVisible(false);
		
		final Slider slider = new Slider(shell, SWT.VERTICAL);
		slider.setMaximum(110);
		slider.setSelection((int) (DataHolder.getFitnessScoreThreshold() * 100));
		addToGrid(slider, 2);
		
		fitnessSliderLabel.setText(fitnessSliderLabel.getText() + slider.getSelection() / 100.0);
		
		slider.addListener (SWT.Selection, new Listener () {
		    public void handleEvent (Event e) {		
		        double value = slider.getSelection() / 100.0;
		        fitnessSliderLabel.setText("Fitness Threshold --->  " + value);
		        DataHolder.setFitnessScoreThreshold(value);
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
        list.add("K-Means Clustering Classifier");
        //list.select(0);

        list.addListener(SWT.Selection, new Listener () {
            public void handleEvent (Event e) {
                String[] items = list.getSelection();
                
                if(items[0].equalsIgnoreCase("Decision Tree Classifier"))
                {
	            	fitnessSliderLabel.setVisible(true);
	            	fitnessSlider.setVisible(true);
	            	runButton.setVisible(true);
                }
                else
                {
                	fitnessSliderLabel.setVisible(false);
	            	fitnessSlider.setVisible(false);
	            	runButton.setVisible(false);
                }
            }
        });

        addToGrid(list, 2);     
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
