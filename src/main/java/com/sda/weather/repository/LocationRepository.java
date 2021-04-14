package com.sda.weather.repository;

import com.sda.weather.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationName(String cityName);
}
