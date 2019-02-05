# iad-j2y2j-converter-maven-plugin

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 
[![Maven Central](https://img.shields.io/maven-central/v/com.github.iarellano/iad-j2y2j-converter-maven-plugin.svg)](https://mvnrepository.com/artifact/com.github.iarellano/iad-j2y2j-converter-maven-plugin) 

## Json to Yaml to Json Converter Maven Plugin

The Json to Yaml to Json Converter Maven Plugin allows to convert files from json to yaml and
vice versa. It makes use of the [Apache Maven Filtering](https://maven.apache.org/shared/maven-filtering/)
component which provides filtering functionality.

## Table of contents

- [Goals](#Goals)
- [Getting started](#Getting-started)
- [Unit testing](#Unit-testing)
- [Integration testing](#Integration-testing)
- [Plugin configuration](#Plugin-configuration)
  - [Json To Yaml Conversion Parameters](#Json-To-Yaml-Conversion-Parameters)
  - [Yaml T Json Conversion Parameters](#Yaml-To-Json-Conversion-Parameters)
  - [Resource Element](#Resource-Element)
- [Examples](#Examples)
  - [Json to Yaml](#Json-to-Yaml)
  - [Yaml to Json](#Yaml-to-Json)

## Goals
This plugin copies the files specified by Resource elements, to an output directory and then converts
them to the specified format. Filtering is applied before files are converted. This plugin has two goals:

- [json-2-yaml](#Json-2-Yaml-Conversion-Parameters) converts json files to yaml
- [yaml-2-json](#Yaml-2-Json-Conversion-Parameters) converts yaml files to json

## Getting started

You can help yourself getting started by checking the integration test in [src/it](src/it) and unit tests in
[src/test/resources/projects-to-test](src/test/resources/projects-to-test). Nevertheless the tables below describe
the configuration parameters.

## Unit testing

Two projects have been implemented for unit testing, you can run them in your system console via ```mvn  clean test``` within the project root directory.

## Integration testing

Another to projects have been implemented fot integraiont testing, to run them in your system console, use ```mvn clean verify -Prun-its``` 

## Plugin configuration

### Json To Yaml Conversion Parameters

| Parameter | Required | Type | Default |  Description |
| --------- | -------- | ---- | ------- | -------------|
| overwrite         | No  | boolean | true | When true and then converted file already exists, it is overwritten, it can also be specified via system property *convert.overwrite*. |
| prettyPrintJson   | No  | boolean | true | When true, json is pretty printed, can also be specified via system property *convert.json.pettyPrint*. |
| indentSpaces      | No  | int     | 4 | Specifies the number of spaces to use for indentation. *prettyPrintJson* has to be true to take effect, it can also be set via system property *convert.json.indentSpaces*. |
| escapeString      | No  | String  | \ | Used to escape filtering tokens, it can also be specified via system property *convert.escapeString*. |
| outputDirectory   | Yes | String (File path) | ${project.build.directory} | Directory where converted files are to be copied to, it can also be specified via system property *convert.outputDirectory*. |
| resources         | Yes | List of [Resource](#Resource-Element) | | List of resources to include/exclude in the conversion. |
| encoding          | No  | String  | ${project.build.sourceEncoding} | Specifies the resources encoding, is uses *file.encoding* if *project.build.sourceEncoding* is not set. |    
| outputExtension   | No  | String  | json | Specifies the extension to use in converted files, it can be specified via system property *convert.json.outputExtension* |    
| escapeWindowsPaths   | No  | boolean  | true | When true, it escapes backslashes and colons in windows-style paths, it can be specified via system property *convert.escapeWindowsPaths* |    

### Yaml To Json Conversion Parameters

| Parameter | Required | Type | Default |  Description |
| --------- | -------- | ---- | ------- | -------------|
| overwrite         | No  | boolean | true | When true and then converted file already exists, it is overwritten, it can also be specified via system property *convert.overwrite*. |
| escapeString      | No  | String  | \ | Used to escape filtering tokens, it can also be specified via system property *convert.escapeString*. |
| outputDirectory   | Yes | String (File path) | ${project.build.directory} | Directory where converted files are to be copied to, it can also be specified via system property *convert.outputDirectory*. |
| resources         | Yes | List of [Resource](#Resource-Element) | | List of resources to include/exclude in the conversion. |
| encoding          | No  | String  | ${project.build.sourceEncoding} | Specifies the resources encoding, is uses *file.encoding* if *project.build.sourceEncoding* is not set. |
| outputExtension   | No  | String  | yaml | Specifies the extension to use in converted files, it can be specified via system property *convert.yml.outputExtension* |
| escapeWindowsPaths   | No  | boolean  | true | When true, it escapes backslashes and colons in windows-style paths, it can be specified via system property *convert.escapeWindowsPaths* |

### Resource Element
Resource element is better expained in [Apache Maven Resources Plugin](https://maven.apache.org/plugins/maven-resources-plugin/index.html) project, follow the links below for detailed information.
- [filtering](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html)
- [includes](https://maven.apache.org/plugins/maven-resources-plugin/examples/include-exclude.html)
- [excludes](https://maven.apache.org/plugins/maven-resources-plugin/examples/include-exclude.html)

## Examples

### Json to Yaml

```xml
<plugin>
    <groupId>com.github.iarellano</groupId>
    <artifactId>iad-j2y2j-converter-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>json-2-yaml</id>
            <phase>process-resources</phase>
            <goals>
                <goal>json-2-yaml</goal>
            </goals>
            <configuration>
                <resources>
                    <resource>
                        <directory>${project.basedir}/src/main</directory>
                        <filtering>true</filtering>
                        <includes>
                            <include>resources/*.json</include>
                        </includes>
                    </resource>
                </resources>
                <outputExtension>yml</outputExtension>
                <outputDirectory>${project.build.directory}/resources</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Yaml to Json
```xml
<plugin>
    <groupId>com.github.iarellano</groupId>
    <artifactId>iad-j2y2j-converter-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>yaml-2-json</id>
            <phase>process-resources</phase>
            <goals>
                <goal>yaml-2-json</goal>
            </goals>
            <configuration>
                <resources>
                    <resource>
                        <directory>${project.basedir}/src/main</directory>
                        <filtering>true</filtering>
                        <includes>
                            <include>resources/*.yaml</include>
                        </includes>
                    </resource>
                </resources>
                <outputExtension>yml</outputExtension>
                <outputDirectory>${project.build.directory}/resources</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```