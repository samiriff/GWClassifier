package org.ck.gui;

import java.awt.Dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class WelcomeWindow {
	private Shell shell;
	//	private Canvas DTCanvas;
	//	private Canvas GACanvas;
	//	private Canvas GWCanvas;

	private Canvas iconTrain;
	private Canvas iconClassify;
	private Canvas iconGit;
	private Canvas iconSettings;
	private Canvas iconExit;

	public WelcomeWindow(Display display)
	{
		shell = new Shell(display);
		initUI();
		initListeners();
		shell.setText("Decision Tree Based Classifier");
		shell.setImage(new Image(display, "Icons/statistics.png"));
		shell.setSize(720,720);
		shell.setLocation(50, 50);
		shell.setBackgroundImage(new Image(display, "Icons/white_background.png"));
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
		//new MainWindow(display);
		display.dispose();
	}
	private void initListeners() {
		iconGit.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("https://www.github.com/samiriff/GWClassifier");
				new BrowserWindow(shell.getDisplay());
			}
		});

		iconExit.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("Exit!");
				System.exit(0);
			}
		});
		iconTrain.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event event) {
				new MainWindow(shell.getDisplay());
				

			}
		});
		iconClassify.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				new ClassifyWindow(shell.getDisplay());
				
			}
		});


	}

	private void initUI() {
		shell.setLayout(new FormLayout());
		Label welcomeLabel = new Label(shell,SWT.LEFT);
		welcomeLabel.setFont(new Font(shell.getDisplay(),"Jokerman",20,SWT.ITALIC));
		FormData formData = new FormData(20,20);
		formData.left = new FormAttachment(20);
		formData.right = new FormAttachment(90);
		formData.top = new FormAttachment(5);
		formData.bottom = new FormAttachment(15);
		welcomeLabel.setText("Decision Tree Based Classifier");
		welcomeLabel.setLayoutData(formData);



		iconTrain = new Canvas(shell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(welcomeLabel, 10, SWT.LEFT);
		formData.right = new FormAttachment(iconTrain, 80, SWT.LEFT);
		formData.top = new FormAttachment(welcomeLabel, 10, SWT.BOTTOM);
		formData.bottom = new FormAttachment(iconTrain, 80, SWT.TOP);
		iconTrain.setLayoutData(formData);
		iconTrain.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Image imageSrc = new Image(shell.getDisplay(), "Icons/update.png");
				if (imageSrc != null) {
					event.gc.drawImage(imageSrc, 0, 0);
				}
			}
		});

		iconClassify = new Canvas(shell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(iconTrain, 0, SWT.LEFT);
		formData.right = new FormAttachment(iconClassify, 80, SWT.LEFT);
		formData.top = new FormAttachment(iconTrain, 10, SWT.BOTTOM);
		formData.bottom = new FormAttachment(iconClassify, 80, SWT.TOP);
		iconClassify.setLayoutData(formData);
		iconClassify.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Image imageSrc = new Image(shell.getDisplay(), "Icons/new.png");
				if (imageSrc != null) {
					event.gc.drawImage(imageSrc, 0, 0);
				}
			}
		});

		iconGit = new Canvas(shell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(iconClassify, 0, SWT.LEFT);
		formData.right = new FormAttachment(iconGit, 80, SWT.LEFT);
		formData.top = new FormAttachment(iconClassify, 10, SWT.BOTTOM);
		formData.bottom = new FormAttachment(iconGit, 80, SWT.TOP);
		iconGit.setLayoutData(formData);
		iconGit.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Image imageSrc = new Image(shell.getDisplay(), "Icons/github.jpg");
				if (imageSrc != null) {
					event.gc.drawImage(imageSrc, 0, 0);
				}
			}
		});

		iconSettings = new Canvas(shell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(iconGit, 0, SWT.LEFT);
		formData.right = new FormAttachment(iconSettings, 80, SWT.LEFT);
		formData.top = new FormAttachment(iconGit, 10, SWT.BOTTOM);
		formData.bottom = new FormAttachment(iconSettings, 80, SWT.TOP);
		iconSettings.setLayoutData(formData);
		iconSettings.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Image imageSrc = new Image(shell.getDisplay(), "Icons/users.png");
				if (imageSrc != null) {
					event.gc.drawImage(imageSrc, 0, 0);
				}
			}
		});
		iconSettings.setVisible(false);

		iconExit = new Canvas(shell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(iconSettings, 0, SWT.LEFT);
		formData.right = new FormAttachment(iconExit, 80, SWT.LEFT);
		formData.top = new FormAttachment(iconSettings, 10, SWT.BOTTOM);
		formData.bottom = new FormAttachment(iconExit, 80, SWT.TOP);
		iconExit.setLayoutData(formData);
		iconExit.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				Image imageSrc = new Image(shell.getDisplay(), "Icons/delete.png");
				if (imageSrc != null) {
					event.gc.drawImage(imageSrc, 0, 0);
				}
			}
		});

		Label exitLabel = new Label(shell,SWT.WRAP);
		formData = new FormData();
		formData.top = new FormAttachment(iconExit, 5 ,SWT.CENTER);
		formData.left = new FormAttachment(iconExit, 10, SWT.RIGHT);
		exitLabel.setLayoutData(formData);
		Font f = new Font(shell.getDisplay(), "Lucida Sans", 16, SWT.BOLD);
		exitLabel.setFont(f);
		exitLabel.setText("Exit Application");


		Label settingsLabel = new Label(shell,SWT.WRAP);
		formData = new FormData();
		formData.top = new FormAttachment(iconSettings, 5 ,SWT.CENTER);
		formData.left = new FormAttachment(iconSettings, 10, SWT.RIGHT);
		settingsLabel.setLayoutData(formData);
		settingsLabel.setFont(f);
		settingsLabel.setText("Dev Info"); settingsLabel.setVisible(false);

		Label viewLabel = new Label(shell,SWT.WRAP);
		formData = new FormData();
		formData.top = new FormAttachment(iconGit, 5 ,SWT.CENTER);
		formData.left = new FormAttachment(iconGit, 10, SWT.RIGHT);
		viewLabel.setLayoutData(formData);
		viewLabel.setFont(f);
		viewLabel.setText("View On Github");


		Label classifyLabel = new Label(shell,SWT.WRAP);
		formData = new FormData();
		formData.top = new FormAttachment(iconClassify, 5 ,SWT.CENTER);
		formData.left = new FormAttachment(iconClassify, 10, SWT.RIGHT);
		classifyLabel.setLayoutData(formData);
		classifyLabel.setFont(f);
		classifyLabel.setText("Classify The Data sets");

		Label trainLabel = new Label(shell,SWT.WRAP);
		trainLabel.setFont(f);
		formData = new FormData();
		formData.top = new FormAttachment(iconTrain, 5 ,SWT.CENTER);
		formData.left = new FormAttachment(iconTrain, 10, SWT.RIGHT);
		trainLabel.setLayoutData(formData);
		trainLabel.setText("Train the Decision Tree");

	}
}
