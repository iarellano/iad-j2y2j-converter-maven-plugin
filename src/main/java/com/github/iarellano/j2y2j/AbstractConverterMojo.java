package com.github.iarellano.j2y2j;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.apache.maven.shared.utils.io.FileUtils;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractConverterMojo extends org.apache.maven.plugin.AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceEncoding}")
    protected String encoding;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession mavenSession;

    @Component
    private MavenResourcesFiltering mavenResourceFiltering;

    @Component(role = MavenFileFilter.class, hint = "default")
    private CustomPluginFileFilter customPluginFileFilter;

    @Parameter(required = true)
    private List<Resource> resources = new ArrayList<>();

    @Parameter(defaultValue = "${project.build.directory}", property = "convert.outputDirectory", required = true)
    protected File outputDirectory;

    @Parameter(defaultValue = "true", property = "convert.json.prettyPrintJson")
    protected boolean prettyPrintJson;

    @Parameter(defaultValue = "4", property = "convert.json.indentSpaces")
    private int indentSpaces;

    @Parameter(defaultValue = "true", property = "convert.overwrite")
    private boolean overwrite;

    @Parameter(defaultValue = "\\", property = "convert.escapeString")
    private String escapeString;

    @Parameter(defaultValue = "true", property = "convert.escapeWindowsPaths")
    private boolean escapeWindowsPaths;

    private ObjectMapper reader;

    private ObjectMapper writer;

    protected abstract ObjectMapper getReader();

    protected abstract ObjectMapper getWriter();

    protected abstract String getFileExtension();

    public void execute() throws MojoExecutionException {

        reader = getReader();
        writer = getWriter();

        customPluginFileFilter.setConverterMojo(this);
        String fileEncoding = encoding;
        if (StringUtils.isEmpty(fileEncoding)) {
            fileEncoding = ReaderFactory.FILE_ENCODING;
        }
        List<String> nonFilters = mavenResourceFiltering.getDefaultNonFilteredFileExtensions();
        List<String> filters = new ArrayList<>();
        MavenResourcesExecution mavenResourcesExecution = new MavenResourcesExecution(resources, outputDirectory, mavenProject, fileEncoding, filters, nonFilters, mavenSession);
        mavenResourcesExecution.setUseDefaultFilterWrappers(true);
        mavenResourcesExecution.setOverwrite(overwrite);
        mavenResourcesExecution.setEscapeString(escapeString);
        mavenResourcesExecution.setEscapeWindowsPaths(escapeWindowsPaths);
        try {
            mavenResourceFiltering.filterResources(mavenResourcesExecution);
        } catch (MavenFilteringException e) {
            e.printStackTrace();
            throw new MojoExecutionException("Could not apply filtering", e);
        }
    }

    public void convert(File sourceFile) throws MavenFilteringException {

        if (sourceFile.isFile() && sourceFile.exists()) {
            String fileName = FileUtils.removeExtension(sourceFile.getAbsolutePath());
            File outputFile = new File(fileName + "." + getFileExtension());
            try {
                String content = FileUtils.fileRead(sourceFile, encoding);
                Object object = reader.readValue(content, Object.class);
                writer.writeValue(outputFile, object);
                getLog().info(String.format("%s file created:  %s", getFileExtension().toUpperCase(), outputFile.getPath()));
            } catch (IOException e) {
                throw new MavenFilteringException("Error converting file " + sourceFile.getAbsolutePath());
            }
            if (!getLog().isDebugEnabled()) {
                sourceFile.delete();
            }
        }
    }

    protected ObjectMapper getJsonWriter() {
        ObjectMapper writer = new ObjectMapper();
        if (isPrettyPrintJsonEnabled()) {
            writer.enable(SerializationFeature.INDENT_OUTPUT);
            if (indentSpaces > 0) {
                char[] indent = new char[indentSpaces];
                Arrays.fill(indent, ' ');
                DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(new String(indent), DefaultIndenter.SYS_LF);
                DefaultPrettyPrinter printer = new NoSpaceBeforeColonPrettyPrinter();
                printer.indentArraysWith(indenter);
                printer.indentObjectsWith(indenter);
                writer.setDefaultPrettyPrinter(printer);
            }
        }
        return writer;
    }

    protected Integer getIndentSpaces() {
        return indentSpaces;
    }

    protected boolean isPrettyPrintJsonEnabled() {
        return prettyPrintJson;
    }
}
