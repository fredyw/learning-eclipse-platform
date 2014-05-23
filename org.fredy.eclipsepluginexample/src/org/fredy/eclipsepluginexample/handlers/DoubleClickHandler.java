package org.fredy.eclipsepluginexample.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.fredy.eclipsepluginexample.views.SampleView;

public class DoubleClickHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        IWorkbenchPage page = window.getActivePage();
        SampleView view = (SampleView) page.findView(SampleView.ID);
        ISelection selection = view.getSite().getSelectionProvider().getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        MessageDialog.openInformation(
            view.getSite().getShell(),
            SampleView.NAME,
            "Double-click detected on " + obj.toString());
        return null;
    }
}
