package com.sda.weather;

import com.sda.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApp {

    @Autowired
    private static WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WeatherApp.class);
        weatherService.getWeatherData("New York");
    }
}
