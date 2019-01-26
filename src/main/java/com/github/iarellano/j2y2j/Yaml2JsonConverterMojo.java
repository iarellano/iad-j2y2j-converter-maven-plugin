package com.github.iarellano.j2y2j;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "yaml-2-json", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class Yaml2JsonConverterMojo
        extends AbstractConverterMojo {

    @Parameter(defaultValue = "json", property = "convert.json.outputExtension")
    private String outputExtension;

    public void execute()
            throws MojoExecutionException {
        super.execute();
    }

    @Override
    protected ObjectMapper getReader() {
        return new ObjectMapper(new YAMLFactory());
    }

    @Override
    protected ObjectMapper getWriter() {
        return getJsonWriter();
    }

    @Override
    protected String getFileExtension() {
        return outputExtension;
    }

}
