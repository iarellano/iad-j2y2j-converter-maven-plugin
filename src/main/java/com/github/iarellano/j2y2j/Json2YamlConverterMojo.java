package com.github.iarellano.j2y2j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "json-2-yaml", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class Json2YamlConverterMojo
        extends AbstractConverterMojo {

    @Parameter(defaultValue = "yaml", property = "convert.yml.outputExtension")
    private String outputExtension;

    @Override
    protected ObjectMapper getReader() {
        return new ObjectMapper();
    }

    @Override
    protected ObjectMapper getWriter() {
        ObjectMapper writer = new YAMLMapper();
        if (isPrettyPrintJsonEnabled()) {
            writer.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return writer;
    }

    @Override
    protected String getFileExtension() {
        return outputExtension;
    }

}
