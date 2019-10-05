package org.psc.sbapp.mojo;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Mojo(name = "start")
public class SpringBootAppMojo extends AbstractMojo {

    @Parameter
    private String springBootAppClassname;

    @Component
    private MavenProject project;

    @Component
    private PluginDescriptor descriptor;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            addDependenciesToClasspath();
        } catch (DependencyResolutionRequiredException | MalformedURLException e) {
            log.error(e.getMessage());
        }

        Class<?> springBootAppClass = null;
        try {
            springBootAppClass = Thread.currentThread().getContextClassLoader().loadClass(springBootAppClassname);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        Optional.ofNullable(springBootAppClass).ifPresent(clazz -> {
            SpringApplication springApplication = new SpringApplication(clazz);
            springApplication.run();
            log.info("after springApplication.run()");
        });
    }

    private void addDependenciesToClasspath() throws DependencyResolutionRequiredException, MalformedURLException {
        List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
        ClassRealm realm = descriptor.getClassRealm();

        for (String element : runtimeClasspathElements) {
            File elementFile = new File(element);
            realm.addURL(elementFile.toURI().toURL());
        }
    }
}
