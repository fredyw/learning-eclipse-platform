package org.fredy;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class JFaceExample {
    public static void main(String[] args) {
        new ListViewExample();
        new TableViewerExample();
        new TreeViewerExample();
        new TextViewerExample();
        new ComboViewerExample();
    }

    private static class ListViewExample {
        public ListViewExample() {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setText("List Viewer Example");
            shell.setBounds(100, 100, 200, 100);
            shell.setLayout(new FillLayout());
            final ListViewer listViewer = new ListViewer(shell, SWT.SINGLE);
            listViewer.setLabelProvider(new PersonListLabelProvider());
            listViewer.setContentProvider(new ArrayContentProvider());
            listViewer.setInput(Person.example());
            listViewer.setSorter(new ViewerSorter() {
                @Override
                public int compare(Viewer viewer, Object p1, Object p2) {
                    return ((Person) p1).lastName.compareToIgnoreCase(((Person) p2).lastName);
                }
            });
            listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                    System.out.println("Selected: " + selection.getFirstElement());
                }
            });
            listViewer.addDoubleClickListener(new IDoubleClickListener() {
                @Override
                public void doubleClick(DoubleClickEvent event) {
                    IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                    System.out.println("Double Clicked: " + selection.getFirstElement());
                }
            });
            
            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();
        }
    }

    private static class PersonListLabelProvider extends LabelProvider {
        @Override
        public Image getImage(Object element) {
            return null;
        }

        @Override
        public String getText(Object element) {
            Person person = (Person) element;
            return person.firstName + " " + person.lastName;
        }
    }

    public static class TableViewerExample {
        public TableViewerExample() {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setText("Table Viewer Example");
            shell.setBounds(100, 100, 325, 200);
            shell.setLayout(new FillLayout());

            final TableViewer tableViewer = new TableViewer(shell, SWT.SINGLE | SWT.FULL_SELECTION);
            final Table table = tableViewer.getTable();
            table.setHeaderVisible(true);
            table.setLinesVisible(true);

            String[] columnNames = new String[] { "First Name", "Last Name", "Age", "Num Children" };
            int[] columnWidths = new int[] { 100, 100, 35, 75 };
            int[] columnAlignments = new int[] { SWT.LEFT, SWT.LEFT, SWT.CENTER, SWT.CENTER };
            for (int i = 0; i < columnNames.length; i++) {
                TableColumn tableColumn = new TableColumn(table, columnAlignments[i]);
                tableColumn.setText(columnNames[i]);
                tableColumn.setWidth(columnWidths[i]);
            }
            tableViewer.setLabelProvider(new PersonTableLabelProvider());
            tableViewer.setContentProvider(new ArrayContentProvider());
            tableViewer.setInput(Person.example());

            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();
        }
    }

    public static class PersonTableLabelProvider extends LabelProvider implements
        ITableLabelProvider {
        @Override
        public Image getColumnImage(Object element, int index) {
            return null;
        }

        @Override
        public String getColumnText(Object element, int index) {
            Person person = (Person) element;
            switch (index) {
            case 0:
                return person.firstName;
            case 1:
                return person.lastName;
            case 2:
                return Integer.toString(person.age);
            case 3:
                return Integer.toString(person.children.length);
            default:
                return "unknown " + index;
            }
        }
    }

    public static class TreeViewerExample {
        public TreeViewerExample() {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setText("Tree Viewer Example");
            shell.setBounds(100, 100, 200, 200);
            shell.setLayout(new FillLayout());

            final TreeViewer treeViewer = new TreeViewer(shell, SWT.SINGLE);
            treeViewer.setLabelProvider(new PersonListLabelProvider());
            treeViewer.setContentProvider(new PersonTreeContentProvider());
            treeViewer.setInput(Person.example());

            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();
        }
    }

    public static class PersonTreeContentProvider extends ArrayContentProvider implements
        ITreeContentProvider {
        @Override
        public Object[] getChildren(Object parentElement) {
            Person person = (Person) parentElement;
            return person.children;
        }

        @Override
        public Object getParent(Object element) {
            Person person = (Person) element;
            return person.parent;
        }

        @Override
        public boolean hasChildren(Object element) {
            Person person = (Person) element;
            return person.children.length > 0;
        }
    }

    private static class Person {
        public String firstName = "John";
        public String lastName = "Doe";
        public int age = 37;
        public Person[] children = new Person[0];
        public Person parent = null;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        public Person(String firstName, String lastName, int age, Person[] children) {
            this(firstName, lastName, age);
            this.children = children;
            for (int i = 0; i < children.length; i++) {
                children[i].parent = this;
            }
        }

        public static Person[] example() {
            return new Person[] {
                new Person("Dan", "Rubel", 41, new Person[] { new Person("Beth", "Rubel", 11),
                    new Person("David", "Rubel", 6) }),
                new Person("Eric", "Clayberg", 42, new Person[] {
                    new Person("Lauren", "Clayberg", 9), new Person("Lee", "Clayberg", 7) }),
                new Person("Mike", "Taylor", 55) };
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }

    public static class TextViewerExample {
        public TextViewerExample() {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setText("Text Viewer Example");
            shell.setBounds(100, 100, 225, 125);
            shell.setLayout(new FillLayout());

            final TextViewer textViewer = new TextViewer(shell, SWT.MULTI | SWT.V_SCROLL);
            String string = "This is plain text\n" + "This is bold text\n" + "This is red text";
            
            IDocument document = new Document(string);
            textViewer.setDocument(document);
            TextPresentation style = new TextPresentation();
            style.addStyleRange(new StyleRange(19, 17, null, null, SWT.BOLD));
            Color red = new Color(null, 255, 0, 0);
            style.addStyleRange(new StyleRange(37, 16, red, null));
            textViewer.changeTextPresentation(style, true);

            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();
        }
    }
    
    public static class ComboViewerExample {
        public ComboViewerExample() {
            Display display = new Display();
            Shell shell = new Shell(display);
            shell.setText("Combo Viewer Example");

            GridLayout layout = new GridLayout(2, false);
            shell.setLayout(layout);

            Label label = new Label(shell, SWT.NONE);
            label.setText("Select a person:");
            final ComboViewer viewer = new ComboViewer(shell, SWT.READ_ONLY);

            // the ArrayContentProvider object does not store any state,
            // therefore you can re-use instances

            viewer.setContentProvider(ArrayContentProvider.getInstance());
            viewer.setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    if (element instanceof Person) {
                        Person person = (Person) element;
                        return person.firstName;
                    }
                    return super.getText(element);
                }
            });

            Person[] persons = new Person[] {
                new Person("John", "Doe", 25),
                new Person("Jane", "Doe", 28),
                new Person("Foo", "Bar", 32)
            };

            // set the input of the Viewer,
            // this input is send to the content provider

            viewer.setInput(persons);

            // react to the selection change of the viewer
            // note that the viewer returns the actual object
            viewer.addSelectionChangedListener(new ISelectionChangedListener() {
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    IStructuredSelection selection = (IStructuredSelection) event
                        .getSelection();
                    if (selection.size() > 0) {
                        System.out.println(((Person) selection.getFirstElement())
                            .lastName);
                    }
                }
            });

            shell.pack();
            shell.open();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();
        }
    }
}
