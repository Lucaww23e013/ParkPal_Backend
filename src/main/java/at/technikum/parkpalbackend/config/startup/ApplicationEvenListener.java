package at.technikum.parkpalbackend.config.startup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@AllArgsConstructor
public class ApplicationEvenListener {

    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new org.springframework.core.io.ClassPathResource("data.sql"));
        populator.execute(dataSource);
        log.info("ApplicationReadyEvent fired Now");
    }

}
