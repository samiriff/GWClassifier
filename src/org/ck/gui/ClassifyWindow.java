package org.ck.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ClassifyWindow {
	private Shell shell;
	
	public ClassifyWindow(Display display)
	{
		shell = new Shell(display);
		shell.open();
		
	}

}
