package org.fredy.eclipsepluginexample.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class SampleNature implements IProjectNature {
    public static final String ID = "org.fredy.eclipsepluginexample.SampleNature";
    private IProject project;
    
    @Override
    public void configure() throws CoreException {
        IProjectDescription desc = project.getDescription();
        List<ICommand> commands = new ArrayList<>(Arrays.asList(desc.getBuildSpec()));
        // check if the builder is already set
        for (ICommand cmd : commands) {
            if (SampleBuilder.ID.equals(cmd.getBuilderName())) {
                return;
            }
        }
        // add a new command if it's not already set
        ICommand cmd = desc.newCommand();
        cmd.setBuilderName(SampleBuilder.ID);
        commands.add(cmd);
        desc.setBuildSpec(commands.toArray(new ICommand[0]));
        project.setDescription(desc, null);
    }

    @Override
    public void deconfigure() throws CoreException {
        IProjectDescription desc = project.getDescription();
        List<ICommand> commands = new ArrayList<>(Arrays.asList(desc.getBuildSpec()));
        // remove if the builder is already set
        Iterator<ICommand> iterator = commands.iterator();
        while (iterator.hasNext()) {
            ICommand cmd = iterator.next();
            if (SampleBuilder.ID.equals(cmd.getBuilderName())) {
                iterator.remove();
            }
        }
        desc.setBuildSpec(commands.toArray(new ICommand[0]));
        project.setDescription(desc, null);
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }
}
