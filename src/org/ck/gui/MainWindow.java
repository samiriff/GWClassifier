package org.ck.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class MainWindow
{
	public static void main(String args[])
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Decision Tree Classifier");
		
		initGridLayout(shell, display);
		
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		
		display.dispose();
	}
	
	private static void initGridLayout(Shell shell, Display display)
	{
	    GridLayout layout = new GridLayout(2, false);
	    shell.setLayout(layout);
	 	    
	    Label label = new Label(shell, SWT.BORDER);
		label.setText("Decision Tree Classifier");
		label.setToolTipText("Code Kshetra Inc.");				
		label.pack();
			
		Button button =  new Button(shell, SWT.PUSH);
		button.addSelectionListener(new SelectionAdapter() {
		  @Override
		  public void widgetSelected(SelectionEvent e) {
		    // Handle the selection event
		    MainClass.sampleCaller2();
		  }
		}); 
		button.setText("Click Me");		
		button.pack();
	    
	    shell.pack();
		
	}
}
