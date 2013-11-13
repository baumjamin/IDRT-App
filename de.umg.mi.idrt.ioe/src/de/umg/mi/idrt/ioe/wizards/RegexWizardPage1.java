package de.umg.mi.idrt.ioe.wizards;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.umg.mi.idrt.idrtimporttool.importidrt.ResourceManager;
import de.umg.mi.idrt.ioe.OntologyTree.FileHandling;
import de.umg.mi.idrt.ioe.commands.OntologyEditor.CombineNodesCommand;
import de.umg.mi.idrt.ioe.misc.Regex;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Benjamin Baum <benjamin(dot)baum(at)med(dot)uni-goettingen(dot)de>
 *         Department of Medical Informatics Goettingen
 *         www.mi.med.uni-goettingen.de
 */
public class RegexWizardPage1 extends WizardPage {
	private TableColumn column_1;
	private TableColumn column_2;
	private TableViewer viewer;
	private Map<Object, Button> buttons;
	private List<Regex> regexs;

	public RegexWizardPage1() {
		super("Edit Regular Expressions");
		setTitle("Edit Regular Expressions");
		setDescription("Edit Regular Expressions");
	}

	@Override
	public void createControl(Composite parent) {

		File file = new File(FileHandling.getCFGFilePath("regex.csv"));
		try {
			CombineNodesCommand.clear();
			CSVReader reader = new CSVReader(new FileReader(file), ';');

			String[] line = reader.readNext();

			while ((line = reader.readNext()) != null) {
				System.out.println("name: " + line[0]);
				System.out.println("regex: " + line[1]);

				Regex regex = new Regex(line[0], line[1]);
				CombineNodesCommand.addRegEx(regex);
			}
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

//		comp.setLayout(new FillLayout(SWT.HORIZONTAL));

		buttons = new HashMap<Object, Button>();
		viewer = new TableViewer(parent,SWT.MULTI | SWT.H_SCROLL
		        | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewer.setColumnProperties(new String[] {});
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(new ArrayContentProvider());
		
		GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.CENTER;
	    gridData.horizontalSpan = 2;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;
	    gridData.horizontalAlignment = GridData.CENTER;
	    viewer.getControl().setLayoutData(gridData);
//		column_2 = new TableColumn(viewer.getTable(), SWT.NONE);
		
		//		column_2.setWidth(50);
		TableViewerColumn saveCol = new TableViewerColumn(viewer, SWT.NONE);
		column_2 = saveCol.getColumn();
		column_2.setResizable(true);
		column_2.setWidth(50);
		saveCol.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public void update(final ViewerCell cell) {
				
				TableItem item = (TableItem) cell.getItem();
				final Button button;
				if (buttons.containsKey(cell.getElement())) {
					button = buttons.get(cell.getElement());
				}
				else {
					if (((Regex)cell.getElement()).getName().isEmpty()) {
						button = new Button((Composite) cell.getViewerRow().getControl(),SWT.PUSH);
						button.setImage(ResourceManager.getPluginImage("de.umg.mi.idrt.ioe", "images/add.gif"));
						button.setToolTipText("Add Regular Expression");
						
						button.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								CombineNodesCommand.addRegEx(new Regex("new","new"));
								setInput();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
							}
						});
					}
					else {
						button = new Button((Composite) cell.getViewerRow().getControl(),SWT.PUSH);
						button.setImage(ResourceManager.getPluginImage("de.umg.mi.idrt.ioe", "images/remove.gif"));
						button.setToolTipText("Remove Regular Expression");
						button.setData(((Regex)cell.getElement()));

						button.addSelectionListener(new SelectionListener() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								Regex regex = (Regex) button.getData();
								CombineNodesCommand.removeRegex(regex);
								button.dispose();
								setInput();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
							}
						});

					}
					buttons.put(cell.getElement(), button);
				}
				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(button , item, cell.getColumnIndex());
				editor.layout();
			}

		});
		

		final TextCellEditor textCellEditor = new TextCellEditor(viewer.getTable(),SWT.NONE);
		TableViewerColumn nameCol = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn column = nameCol.getColumn();
		column.setMoveable(true);
		column.setText("Name");
		column.setWidth(100);
		nameCol.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				Regex p = (Regex)element;
				return p.getName();
			}

		});
		nameCol.setEditingSupport(new EditingSupport(viewer) {

			protected boolean canEdit(Object element) {
				if (!((Regex)element).getName().isEmpty() && !((Regex)element).getRegex().isEmpty())
					return true;
				else
					return false;
			}

			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			protected Object getValue(Object element) {
				return ((Regex) element).getName();
			}

			protected void setValue(Object element, Object value) {
				((Regex)element).setName((String)value);
				viewer.update(element, null);
			}
		});

		


		TableViewerColumn regexCol = new TableViewerColumn(viewer, SWT.NONE);
		column_1 = regexCol.getColumn();
		column_1.setMoveable(true);
		column_1.setText("Regex");
		column_1.setWidth(300);
		regexCol.setEditingSupport(new EditingSupport(viewer) {

			protected boolean canEdit(Object element) {
				if (!((Regex)element).getName().isEmpty() && !((Regex)element).getRegex().isEmpty())
					return true;
				else
					return false;
			}

			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			protected Object getValue(Object element) {
				return ((Regex) element).getRegex();
			}

			protected void setValue(Object element, Object value) {
				((Regex)element).setRegex((String)value);
				viewer.update(element, null);
			}
		});
		regexCol.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				Regex p = (Regex)element;
				return p.getRegex();
			}

		});

		

		TableViewerColumn testCol = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn test = testCol.getColumn();
		test.setMoveable(true);
		test.setText("Test String");
		test.setWidth(100);
		testCol.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				return ((Regex) element).getTest();
			}

		});
		testCol.setEditingSupport(new EditingSupport(viewer) {
			protected boolean canEdit(Object element) {
				if (!((Regex)element).getName().isEmpty() && !((Regex)element).getRegex().isEmpty())
					return true;
				else
					return false;
			}

			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			protected Object getValue(Object element) {
				return ((Regex) element).getTest();
			}

			protected void setValue(Object element, Object value) {
				((Regex)element).setTest((String)value);
				viewer.update(element, null);
				viewer.refresh();
			}
		});

		TableViewerColumn checkCol = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn check = checkCol.getColumn();
		check.setMoveable(true);
		check.setText("Check");
		check.setWidth(100);
		check.setAlignment(SWT.CENTER);
		checkCol.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public Image getImage(Object element) {
				if (!((Regex)element).getRegex().isEmpty()) {
					if (((Regex)element).check())
						return ResourceManager.getPluginImage("de.umg.mi.idrt.ioe", "images/itemstatus-checkmark16.png");
					else
						return ResourceManager.getPluginImage("de.umg.mi.idrt.ioe", "images/remove-grouping.png");
				}
				else {
					return null;
				}
			}
			@Override
			public String getText(Object element) {
				return "";
			}
		});
//		column_2.pack();
		setInput();
		setControl(parent);
		setPageComplete(true);
	}

	private void setInput() {
		for (Button b : buttons.values()) {
			b.dispose();
		}
		buttons.clear();
		regexs = new ArrayList<Regex>();
		for (Regex regex : CombineNodesCommand.getRegex()) {
			regexs.add(regex);
		}

		regexs.add(new Regex("", ""));
		viewer.setInput(regexs);
		viewer.refresh();
	}
}