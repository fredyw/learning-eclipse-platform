package org.fredy.eclipsepluginexample.builders;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.fredy.eclipsepluginexample.Activator;

public class SampleVisitor implements IResourceProxyVisitor {
    @Override
    public boolean visit(IResourceProxy proxy) throws CoreException {
        if (proxy.getName() != null && proxy.getName().endsWith(".sample")) {
            processResource(proxy.requestResource());
        }
        return true;
    }
    
    private void processResource(IResource resource) throws CoreException {
        if (!(resource instanceof IFile) || !resource.exists()) {
            return;
        }
        
        IFile file = (IFile) resource;
        BufferedInputStream bais = new BufferedInputStream(file.getContents());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int bytesRead;
            byte[] bytes = new byte[4098];
            while ((bytesRead = bais.read(bytes)) != -1) {
                baos.write(bytes, 0, bytesRead);
            }
           
            String fileName = file.getName();
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".sample"))
                + ".base64";
            IFile base64File = file.getParent().getFile(new Path(newFileName));
            String encodedBase64 = new String(Base64.encodeBase64(baos.toByteArray()), "UTF-8");
            ByteArrayInputStream content = new ByteArrayInputStream(encodedBase64.getBytes());
            if (base64File.exists()) {
                base64File.setContents(content, true, false, null);
            } else {
                base64File.create(content, true, null);
            }
            base64File.setDerived(true, null);
        } catch (IOException e) {
            throw new CoreException(new Status(Status.ERROR,
                Activator.PLUGIN_ID, "Failed to generate resource", e));
        } finally {
            try {
                baos.close();
                bais.close();
            } catch (IOException e) {
                throw new CoreException(new Status(Status.ERROR,
                    Activator.PLUGIN_ID, "Failed to close an IO", e));
            }
        }
    }
}
