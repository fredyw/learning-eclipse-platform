package org.fredy.eclipsepluginexample.builders;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class SampleBuilder extends IncrementalProjectBuilder {
    public static final String ID = "org.fredy.eclipsepluginexample.SampleBuilder";

    @Override
    protected IProject[] build(int kind, Map<String, String> args,
        IProgressMonitor monitor) throws CoreException {
        getProject().accept(new SampleVisitor(), IResource.NONE);
        
        return null;
    }
}
