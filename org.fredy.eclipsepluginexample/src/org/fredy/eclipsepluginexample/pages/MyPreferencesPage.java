package org.fredy.eclipsepluginexample.pages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MyPreferencesPage extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage {
    public MyPreferencesPage() {
        super(GRID);
    } 
    
    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected void createFieldEditors() {
    }
}
