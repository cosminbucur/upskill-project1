package com.sda.weather.service;

import com.sda.weather.dto.LocationRequest;
import com.sda.weather.dto.WeatherResponse;
import com.sda.weather.model.Location;
import com.sda.weather.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

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
        Location location = new Location(
                request.getLocationName(),
                request.getLatitude(),
                request.getLongitude(),
                request.getRegion(),
                request.getCountryName()
        );

        return locationRepository.save(location);
    }
}
