package com.sda.weather.info.repository;

import com.sda.weather.info.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationName(String cityName);
}
