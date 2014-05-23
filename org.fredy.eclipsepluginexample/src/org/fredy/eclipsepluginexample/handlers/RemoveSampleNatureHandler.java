package org.fredy.eclipsepluginexample.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.fredy.eclipsepluginexample.builders.SampleNature;

public class RemoveSampleNatureHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof IStructuredSelection) {
            Iterator<?> it = ((IStructuredSelection) selection).iterator();
            while (it.hasNext()) {
                Object o = (Object) it.next();
                if (o instanceof IProject) {
                    try {
                        removeProjectNature((IProject) o, SampleNature.ID);
                    } catch (CoreException e) {
                        throw new ExecutionException("Failed ot remove nature on "
                            + o, e);
                    }
                }
            }
        }
        return null;
    }
    
    private void removeProjectNature(IProject project, String nature) throws CoreException {
        IProjectDescription desc = project.getDescription();
        List<String> natures = new ArrayList<>(Arrays.asList(desc.getNatureIds()));
        if (natures.contains(nature)) {
            natures.remove(nature);
            desc.setNatureIds(natures.toArray(new String[0]));
            project.setDescription(desc, null);
        }
    }
}
