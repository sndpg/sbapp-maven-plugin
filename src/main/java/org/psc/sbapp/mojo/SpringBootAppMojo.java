package org.psc.sbapp.mojo;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.psc.sbapp.config.SpringBootApp;
import org.springframework.boot.SpringApplication;

@Slf4j
@Mojo(name = "start")
public class SpringBootAppMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        SpringApplication springApplication = new SpringApplication(SpringBootApp.class);
        springApplication.run();
        log.info("after springApplication.run()");
    }
}
