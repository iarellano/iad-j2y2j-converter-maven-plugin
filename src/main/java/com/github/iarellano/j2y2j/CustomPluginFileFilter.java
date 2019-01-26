package com.github.iarellano.j2y2j;

import org.apache.maven.shared.filtering.DefaultMavenFileFilter;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.utils.io.FileUtils;
import org.codehaus.plexus.component.annotations.Component;

import java.io.File;
import java.util.List;

@Component(role = MavenFileFilter.class, hint = "default")
public class CustomPluginFileFilter extends DefaultMavenFileFilter {

    private AbstractConverterMojo coverterMojo;

    public CustomPluginFileFilter() {
        super();
    }

    public void setConverterMojo(AbstractConverterMojo converterMojo) {
        this.coverterMojo = converterMojo;
    }

    @Override
    public void copyFile(File from, File to, boolean filtering, List<FileUtils.FilterWrapper> filterWrappers, String encoding, boolean overwrite) throws MavenFilteringException {
        super.copyFile(from, to, filtering, filterWrappers, encoding, overwrite);
        if (coverterMojo != null) {
            coverterMojo.convert(to);
        }
    }
}
