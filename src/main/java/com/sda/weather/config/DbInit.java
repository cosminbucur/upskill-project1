package com.sda.weather.config;

import com.sda.weather.model.Location;
import com.sda.weather.model.WeatherInfo;
import com.sda.weather.repository.LocationRepository;
import com.sda.weather.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            Location location = new Location("test", 123.4, 234.5, "region", "country");
            locationRepository.save(location);

            WeatherInfo weatherInfo = new WeatherInfo(10, 12, 13, "N", 20);
            weatherInfo.setLocation(location);
            weatherInfoRepository.save(weatherInfo);
        };
    }
}
