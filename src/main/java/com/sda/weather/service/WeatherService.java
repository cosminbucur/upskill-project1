package com.sda.weather.service;

import com.sda.weather.dto.LocationRequest;
import com.sda.weather.dto.WeatherResponse;
import com.sda.weather.model.Location;
import com.sda.weather.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    public static final String WEATHER_STACK_API = "http://api.weatherstack.com";
    public static final String WEATHER_STACK_API_KEY = "dfd5e23a0233023e0f00762809f5f327";

    private final RestTemplate restTemplate;
    private final LocationRepository locationRepository;

    @Autowired
    public WeatherService(RestTemplate restTemplate, LocationRepository locationRepository) {
        this.restTemplate = restTemplate;
        this.locationRepository = locationRepository;
    }

    /*
    https://api.weatherstack.com/current?access_key=dfd5e23a0233023e0f00762809f5f327&query=New York
     */
    public WeatherResponse getWeatherData(String location){
        String url = WEATHER_STACK_API + "/current" + "?access_key=" + WEATHER_STACK_API_KEY + "&query=" + location;
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        // convert http response to entity

        // save to DB

        return response;
    }

    public Location save(LocationRequest request) {
        log.info("save location: {}", request);
        Location location = new Location(
                request.getLocationName(),
                request.getLatitude(),
                request.getLongitude(),
                request.getRegion(),
                request.getCountryName()
        );

        return locationRepository.save(location);
    }

    public List<Location> findAll() {
        log.info("find all locations");
        return locationRepository.findAll();
    }
}
