package com.sda.weather.info.controller;

import com.sda.weather.info.dto.LocationRequest;
import com.sda.weather.info.dto.WeatherApiResponse;
import com.sda.weather.info.model.Location;
import com.sda.weather.info.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<WeatherApiResponse> getCurrentWeather(@PathVariable String locationName) {
        return ResponseEntity.ok(weatherService.getWeatherData(locationName));
    }

    @PostMapping("/location")
    public ResponseEntity<Location> saveLocation(@RequestBody @Valid LocationRequest request) {
        return ResponseEntity.ok(weatherService.save(request));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Location>> findAllLocations() {
        return ResponseEntity.ok(weatherService.findAll());
    }

    @GetMapping("/location/{cityName}")
    public ResponseEntity<Location> findByCityName(@PathVariable String cityName) {
        return ResponseEntity.ok(weatherService.findByCityName(cityName));
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Location> updateLocation(@RequestBody @Valid LocationRequest updateRequest,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(weatherService.update(id, updateRequest));
    }
}
