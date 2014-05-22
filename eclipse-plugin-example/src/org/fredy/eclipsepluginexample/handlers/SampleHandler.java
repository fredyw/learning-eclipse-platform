package org.fredy.eclipsepluginexample.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressConstants2;
import org.fredy.eclipsepluginexample.Activator;
import org.osgi.service.prefs.Preferences;

public class SampleHandler extends AbstractHandler {
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        Preferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
        final String message = preferences.get("message", "");
        
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            IResource resource = (IResource) structuredSelection.getFirstElement();
            if (resource != null) {
                System.out.println(resource.getLocation().toFile().getAbsolutePath());
            }
        }
        
        IWorkbenchPart workbenchPart = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getActivePage().getActivePart();
        IEditorPart editor = workbenchPart.getSite().getPage().getActiveEditor();
        if (editor != null) {
            IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
            System.out.println(file.getLocation().toFile().getAbsolutePath());
        }
        
        Job job = new Job("About to say hello") {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                monitor.beginTask("Preparing", 5000);
                try {
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(1000);
                        monitor.worked(1000);
                    }
                } catch (InterruptedException e) {}
                finally {
                    monitor.done();
                }
                
                Display.getDefault().asyncExec(new Runnable() {
                    public void run() {
                        MessageDialog.openInformation(
                            window.getShell(),
                            "Eclipse Plugin Example",
                            message);
                    }
                });
                return Status.OK_STATUS;
            }
        };
        ICommandService service = (ICommandService) PlatformUI.getWorkbench()
            .getService(ICommandService.class);
        Command command = service.getCommand(
            "org.fredy.eclipsepluginexample.ui.commands.sampleCommand");
        if (command != null) {
            job.setProperty(IProgressConstants2.COMMAND_PROPERTY,
                ParameterizedCommand.generateCommand(command, null));
        }
        job.setProperty(IProgressConstants2.ICON_PROPERTY,
            ImageDescriptor.createFromURL(
                SampleHandler.class.getResource("/icons/sample.gif")));
        job.schedule();
        return null;
    }
}
