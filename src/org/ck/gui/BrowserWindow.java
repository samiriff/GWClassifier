package org.ck.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BrowserWindow {
	private Browser browser;
	private Shell shell;
	public BrowserWindow(Display display)
	{
		shell = new Shell(display);
		initUI();
		
		shell.setSize(720, 720);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}		
	}
	private void initUI() {
		FillLayout fillLayout = new FillLayout();
		shell.setLayout(fillLayout);
		browser = new Browser(shell,SWT.NONE);
		browser.setUrl("https://www.github.com/samiriff/GWClassifier");
		
		
	}

}
