package com.sda.weather.info.dto;

public class WeatherApiResponse {

    private ApiLocation location;
    private Current current;

    public ApiLocation getLocation() {
        return location;
    }

    public void setLocation(ApiLocation location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
