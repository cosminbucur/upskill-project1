package com.sda.weather.config;

import com.sda.weather.model.Location;
import com.sda.weather.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit {

    // save location after application starts
    @Autowired
    private LocationRepository locationRepository;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            Location location = new Location("test", 123.4, 234.5, "region", "country");
            locationRepository.save(location);
        };
    }
}
