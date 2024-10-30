package at.technikum.parkpalbackend.config.startup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@AllArgsConstructor
public class ApplicationEvenListener {

    private final DataSource dataSource;
    private final Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("Active Profiles: {}", (Object) activeProfiles);
        boolean isTestProfileActive = false;
        for (String profile : activeProfiles) {
            if ("test".equalsIgnoreCase(profile)) {
                isTestProfileActive = true;
                break;
            }
        }
        if (!isTestProfileActive) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new org.springframework.core.io.ClassPathResource("data.sql"));
            populator.execute(dataSource);
            log.info("Database populated.");
        } else {
            log.info("Skipping database population for test profile.");
        }
    }
}
