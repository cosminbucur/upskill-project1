package com.sda.weather.service;

import com.sda.weather.dto.ApiLocation;
import com.sda.weather.dto.Current;
import com.sda.weather.dto.LocationRequest;
import com.sda.weather.dto.WeatherApiResponse;
import com.sda.weather.model.Location;
import com.sda.weather.model.WeatherInfo;
import com.sda.weather.repository.LocationRepository;
import com.sda.weather.repository.WeatherInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    public static final String WEATHER_STACK_API = "http://api.weatherstack.com";
    public static final String WEATHER_STACK_API_KEY = "dfd5e23a0233023e0f00762809f5f327";

    private final RestTemplate restTemplate;
    private final LocationRepository locationRepository;
    private final WeatherInfoRepository weatherInfoRepository;

    @Autowired
    public WeatherService(RestTemplate restTemplate,
                          LocationRepository locationRepository,
                          WeatherInfoRepository weatherInfoRepository) {
        this.restTemplate = restTemplate;
        this.locationRepository = locationRepository;
        this.weatherInfoRepository = weatherInfoRepository;
    }

    /*
    https://api.weatherstack.com/current?access_key=dfd5e23a0233023e0f00762809f5f327&query=New York
     */
    public WeatherApiResponse getWeatherData(String location) {
        Location existingLocation = locationRepository.findByLocationName(location);
        String url = WEATHER_STACK_API + "/current" + "?access_key=" + WEATHER_STACK_API_KEY + "&query=" + location;
        if (existingLocation == null) {
            // call api
            WeatherApiResponse apiResponse = restTemplate.getForObject(url, WeatherApiResponse.class);

            // create location
            ApiLocation apiLocation = apiResponse.getLocation();

            // convert http response to entity
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setLocationName(apiLocation.getName());
            locationRequest.setLatitude(Double.parseDouble(apiLocation.getLatitude()));
            locationRequest.setLongitude(Double.parseDouble(apiLocation.getLongitude()));
            locationRequest.setRegion(apiLocation.getRegion());
            locationRequest.setCountryName(apiLocation.getCountry());

            save(locationRequest);

            saveWeatherInfo(apiResponse);
            return apiResponse;
        } else {
            // existing location

            // check for date and location
            WeatherInfo foundWeatherInfo = weatherInfoRepository.findByLocationIdAndDate(existingLocation.getId(), LocalDate.now());
            if (foundWeatherInfo != null) {
                WeatherApiResponse weatherApiResponse = new WeatherApiResponse();
                ApiLocation apiLocation = new ApiLocation();
                apiLocation.setName(existingLocation.getLocationName());
                apiLocation.setCountry(existingLocation.getCountryName());

                weatherApiResponse.setLocation(apiLocation);

                Current current = new Current();
                current.setTemperature(foundWeatherInfo.getTemperature());
                current.setHumidity(foundWeatherInfo.getHumidity());
                weatherApiResponse.setCurrent(current);

                return weatherApiResponse;
            } else {
                WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
                saveWeatherInfo(response);
                return response;
            }
        }
    }

    public WeatherInfo saveWeatherInfo(WeatherApiResponse weatherApiResponse) {
        // TODO: set location on weather info
        Current current = weatherApiResponse.getCurrent();
        WeatherInfo weatherInfo = new WeatherInfo(
                current.getTemperature(),
                current.getPressure(),
                current.getHumidity(),
                current.getWindDirection(),
                current.getWindSpeed()
        );

        return weatherInfoRepository.save(weatherInfo);
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

    public Location update(Long id, LocationRequest updateRequest) {
        log.info("update location: {}, with data: {}", id, updateRequest);

        Location foundLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("location not found"));

        foundLocation.setLocationName(updateRequest.getLocationName());
        foundLocation.setLatitude(updateRequest.getLatitude());
        foundLocation.setLongitude(updateRequest.getLongitude());
        foundLocation.setRegion(updateRequest.getRegion());
        foundLocation.setCountryName(updateRequest.getCountryName());

        return locationRepository.save(foundLocation);
    }

    public Location findByCityName(String cityName) {
        log.info("find location by city name: {}", cityName);
        return locationRepository.findByLocationName(cityName);
    }
}
