package com.sda.weather.controller;

import com.sda.weather.dto.WeatherResponse;
import com.sda.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.sda.weather.service.WeatherService.WEATHER_STACK_API;
import static com.sda.weather.service.WeatherService.WEATHER_STACK_API_KEY;

@RequestMapping("/weather")
@RestController
public class Controller {

    private final WeatherService weatherService;
    private final RestTemplate restTemplate;

    @Autowired
    public Controller(WeatherService weatherService, RestTemplate restTemplate) {
        this.weatherService = weatherService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{locationName}")
    public String getCurrentWeather(@PathVariable String locationName) {
        String url = WEATHER_STACK_API + "/current" + "?access_key=" + WEATHER_STACK_API_KEY + "&query=" + locationName;
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        // convert http response to entity

        // save to DB

        return "ok";
    }
}
