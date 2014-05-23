package org.fredy.eclipsepluginexample.pages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.fredy.eclipsepluginexample.Activator;

public class SamplePreferencePage extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage {
    public SamplePreferencePage() {
        super(GRID);
    }
    
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    @Override
    protected void createFieldEditors() {
        addField(new IntegerFieldEditor("launchCount",
            "Number of times it has been launched",
            getFieldEditorParent()));
        addField(new StringFieldEditor("message", "Message to be displayed",
            getFieldEditorParent()));
    }
}
