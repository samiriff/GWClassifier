package org.ck.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Test {


	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout());
		Table table = new Table(shell, SWT.BORDER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn column0 = new TableColumn(table, SWT.NONE);
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		TableColumn column2 = new TableColumn(table, SWT.NONE);
		int minWidth = 0;
		for (int i = 0; i < 100; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {"item "+i, "asdadasd", "sada d asd asd a dasd"});
			if (i %3 == 0 || i % 5 == 0) {
				Button b = new Button(table, SWT.CHECK);
				b.pack();
				TableEditor editor = new TableEditor(table);
				Point size = b.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				editor.minimumWidth = size.x;
				minWidth = Math.max(size.x, minWidth);
				editor.minimumHeight = size.y;
				editor.horizontalAlignment = SWT.RIGHT;
				editor.verticalAlignment = SWT.CENTER;
				if (i % 3 == 0) {
					editor.setEditor(b, item , 1);
				} else {
					editor.setEditor(b, item , 2);
				}
			}
		}
		column0.pack();
		column1.pack();
		column1.setWidth(column1.getWidth() + minWidth);
		column2.pack();
		column2.setWidth(column2.getWidth() + minWidth);
		shell.setSize(300, 300);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}