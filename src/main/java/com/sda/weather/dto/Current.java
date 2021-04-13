package com.sda.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Current {

    private Integer temperature;
    private Integer pressure;
    @JsonProperty("wind_dir")
    private String windDirection;
    @JsonProperty("wind_speed")
    private Integer windSpeed;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }
}
