package org.psc.sbapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootConfiguration
public class SpringBootApp {

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        log.info("spring boot app within maven plugin mojo execution started...");
    }

}
