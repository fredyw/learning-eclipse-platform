package org.fredy.eclipsepluginexample.editors;

import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class SampleEditor extends AbstractTextEditor  {
    public SampleEditor() {
        setDocumentProvider(new TextFileDocumentProvider());
    }
}
