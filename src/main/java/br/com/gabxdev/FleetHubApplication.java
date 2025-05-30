package br.com.gabxdev;

import br.com.gabxdev.core.properties.FleetHubProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FleetHubProperties.class})
public class FleetHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetHubApplication.class, args);
    }

}
