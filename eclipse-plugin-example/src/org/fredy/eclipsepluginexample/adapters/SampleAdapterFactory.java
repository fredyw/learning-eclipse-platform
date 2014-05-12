package org.fredy.eclipsepluginexample.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

@SuppressWarnings("rawtypes")
public class SampleAdapterFactory implements IAdapterFactory {
    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType == IPropertySource.class && adaptableObject instanceof Sample) {
            return new SamplePropertySource((Sample) adaptableObject);
        }
        return null;
    }

    @Override
    public Class[] getAdapterList() {
        return new Class[] { IPropertySource.class };
    }
}
