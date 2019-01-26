package com.github.iarellano.j2y2j;


import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class Json2YamlMojoTest {
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testJson2Yaml()
            throws Exception {
        File pom = new File("target/test-classes/projects-to-test/json-2-yaml/");

        assertNotNull(pom);
        assertTrue(pom.exists());

        Json2YamlConverterMojo json2YamlConverterMojo = (Json2YamlConverterMojo) rule.lookupConfiguredMojo(pom, "json-2-yaml");
        MavenSession session = (MavenSession) rule.getVariableValueFromObject(json2YamlConverterMojo, "mavenSession");
        assertNotNull(session);

        session.getSystemProperties().putAll(System.getProperties());

        assertNotNull(json2YamlConverterMojo);
        json2YamlConverterMojo.execute();

        String outputExtension = (String) rule.getVariableValueFromObject(json2YamlConverterMojo, "outputExtension");

        File outputDirectory = (File) rule.getVariableValueFromObject(json2YamlConverterMojo, "outputDirectory");
        assertNotNull(outputDirectory);

        File yamlFile = new File(outputDirectory, "input." + outputExtension);
        assertTrue(yamlFile.exists());
        assertTrue(yamlFile.isFile());
    }

    /**
     * @throws Exception if any
     */
    @Test
    public void testYaml2Json()
            throws Exception {
        File pom = new File("target/test-classes/projects-to-test/yaml-2-json/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        Yaml2JsonConverterMojo yaml2JsonConverterMojo = (Yaml2JsonConverterMojo) rule.lookupConfiguredMojo(pom, "yaml-2-json");

        int indentSpaces = (int) rule.getVariableValueFromObject(yaml2JsonConverterMojo, "indentSpaces");
        assertEquals(indentSpaces, 4);

        boolean prettyPrintJson = (boolean) rule.getVariableValueFromObject(yaml2JsonConverterMojo, "prettyPrintJson");
        assertFalse(prettyPrintJson);

        MavenSession session = (MavenSession) rule.getVariableValueFromObject(yaml2JsonConverterMojo, "mavenSession");
        assertNotNull(session);

        session.getSystemProperties().putAll(System.getProperties());

        assertNotNull(yaml2JsonConverterMojo);
        yaml2JsonConverterMojo.execute();


        File outputDirectory = (File) rule.getVariableValueFromObject(yaml2JsonConverterMojo, "outputDirectory");
        assertNotNull(outputDirectory);

        File yamlFile = new File(outputDirectory, "input.json");
        assertTrue(yamlFile.exists());
        assertTrue(yamlFile.isFile());
    }
}

