package org.fredy.eclipsepluginexample.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SampleHandler extends AbstractHandler {
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
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
                            "Hello, Eclipse world");
                    }
                });
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        return null;
    }
}
