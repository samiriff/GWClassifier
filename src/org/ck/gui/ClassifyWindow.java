package org.ck.gui;

import org.ck.ga.Genome;
import org.ck.gui.Constants.DatasetOptions;
import org.ck.sample.DataHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ClassifyWindow {
	private Shell shell;
	
	private int gridHorizontalSpacing = 10;
	private int gridVerticalSpacing = 4;
	private int gridMarginBottom = 5;
	private int gridMarginTop = 5;
	private int gridPadding = 2;
	
	private List featureList;
	
	public ClassifyWindow(Display display)
	{
		shell = new Shell(display);
		
		initUI();
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
	}
	
	private void initUI() {
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.horizontalSpacing = gridHorizontalSpacing;
		gridLayout.verticalSpacing = gridVerticalSpacing;
		gridLayout.marginBottom = gridMarginBottom;
		gridLayout.marginTop = gridMarginTop;
		shell.setLayout(gridLayout);
		shell.setText("Classifier Window");
		
		initDataLoaderPart();
		initFeatureReader();
		
        
		
	}

	private void initFeatureReader() {
		Label features = new Label(shell, SWT.NONE);
		features.setText("Features");
		featureList = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		featureList.setItems(new String[] { "Best of Breed", "Prettiest Female",
				"Handsomest Male", "Best Dressed", "Fluffiest Ears",
				"Most Colors", "Best Performer", "Loudest Bark",
				"Best Behaved", "Prettiest Eyes", "Most Hair", "Longest Tail",
				"Cutest Trick" });
		
	}

	private void initDataLoaderPart() {
		Group dataLoader = new Group(shell, SWT.NONE);
		dataLoader.setText("Load/Build Decision Tree");
		GridLayout dataLoaderLayout = new GridLayout(2,true);
		dataLoaderLayout.marginWidth = 5;
		dataLoaderLayout.marginHeight = 5;
		dataLoader.setLayout(dataLoaderLayout);
			
		Label dogName = new Label(dataLoader, SWT.NONE|SWT.CENTER);
		dogName.setText("Select Dataset");
		final Combo combo = new Combo(dataLoader, SWT.DROP_DOWN);
        for(int i = 0; i < DatasetOptions.values().length; i++)
        	combo.add("" + DatasetOptions.values()[i]);

        combo.select(0);
        combo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	DataHolder.setDataset(DatasetOptions.valueOf(combo.getText()));
            	Genome.reInitializeStaticVariables();
            };
        });
        
        Label savedValues = new Label(dataLoader, SWT.NONE|SWT.CENTER);
		savedValues.setText("Select Decision Tree");
		final Combo combo2 = new Combo(dataLoader, SWT.DROP_DOWN);
        combo2.add("Need to initalize from the file");
        combo2.select(0);
        combo2.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	//DataHolder.setDataset(DatasetOptions.valueOf(combo2.getText()));
            	//Genome.reInitializeStaticVariables();
            };
        });
        Label infoLabel = new Label(shell,SWT.None | SWT.BORDER_SOLID);
        infoLabel.setText("General Information Label");
		
	}

	public static void main(String args[])
	{
		new ClassifyWindow(new Display());
		
	}

}
