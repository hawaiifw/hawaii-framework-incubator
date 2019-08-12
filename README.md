## Hawaii Framework Incubator

The Hawaii Framework is a Java framework for developing Spring based applications.

## License
The Hawaii Framework is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0

## Building

At the moment, this artifact is not published to a maven repository, so you need to build
it yourself and publish it to your local maven repository by running

    ./gradlew clean build publishToMavenLocal

## Hawaii Framework Incubator and Hawaii Framework
A few things need to be considered when using Hawaii Framework in combination with Hawaii Framework Incubator.
Classes in Hawaii Framework Incubator could override classes in Hawaii Framework. 
This introduces a problem when importing both projects. It is important that the developer has control over what class is used.
Meaning the order of classes in the class path becomes important. Below are a few things that should be done or kept in mind.

0. Put the Hawaii Framework Incubator dependency above all Hawaii Framework dependencies.
    0. This is needed to be able to run correctly inside an IDE (for example Intellij) and building via the command line.
0. Put the following in the build.gradle:

    ```
    bootJar {
        with copySpec {
            into "/lib-overload"
            from configurations.runtime.filter { it.name.contains("hawaii-framework-incubator") }
        }
        manifest {
            attributes 'Main-Class': 'org.springframework.boot.loader.PropertiesLauncher'
            attributes 'Loader-Path': '/lib-overload,lib'
        }
    }
    ```
    0. This is executed when creating a jar, this will force Hawaii Framework Incubator to be loaded before the `lib` director
    inside the jar. 

