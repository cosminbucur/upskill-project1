package com.sda.weather.controller;

import com.sda.weather.dto.LocationRequest;
import com.sda.weather.dto.WeatherResponse;
import com.sda.weather.model.Location;
import com.sda.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/location")
    public ResponseEntity<Location> saveLocation(@RequestBody @Valid LocationRequest request) {
        return ResponseEntity.ok(weatherService.save(request));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> findAllLocations() {
        List<Location> responseBody = weatherService.findAll();
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Location> updateLocation(@RequestBody @Valid LocationRequest updateRequest,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(weatherService.update(id, updateRequest));
    }
}
