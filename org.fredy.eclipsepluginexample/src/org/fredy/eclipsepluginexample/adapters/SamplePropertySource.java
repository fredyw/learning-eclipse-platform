package org.fredy.eclipsepluginexample.adapters;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class SamplePropertySource implements IPropertySource {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private final Sample sample;
    
    public SamplePropertySource(Sample sample) {
        this.sample = sample;
    }
    
    @Override
    public Object getEditableValue() {
        return this;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {
            new TextPropertyDescriptor(NAME, "Name"),
            new TextPropertyDescriptor(DESCRIPTION, "Description")
        };
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (id.equals(NAME)) {
            return sample.getName();
        } else if (id.equals(DESCRIPTION)) {
            return sample.getDescription();
        } else {
            return null;
        }
    }

    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (id.equals(NAME)) {
            sample.setName((String) value);
        } else if (id.equals(DESCRIPTION)) {
            sample.setDescription((String) value);
        }
    }
}
