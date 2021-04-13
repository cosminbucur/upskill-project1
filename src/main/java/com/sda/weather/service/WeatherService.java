package com.sda.weather.service;

import com.sda.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    public static final String WEATHERSTACK_CURRENT = "http://api.weatherstack.com/current";
    public static final String WEATHERSTACK_API_KEY = "dfd5e23a0233023e0f00762809f5f327";


    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeatherData(String location){

        //perform http get
        //    ? access_key = YOUR_ACCESS_KEY
        //                & query = New York)
        WeatherResponse response = restTemplate.getForObject(WEATHERSTACK_CURRENT + "?access_key=" + WEATHERSTACK_API_KEY + "&query=" + location, WeatherResponse.class);
        return response;
        //https://api.weatherstack.com/current?access_key=dfd5e23a0233023e0f00762809f5f327&query=New York
        //convert http response to entity

        //save to DB
    }
}
