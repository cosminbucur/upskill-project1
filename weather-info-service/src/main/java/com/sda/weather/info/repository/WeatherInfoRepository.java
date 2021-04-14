package com.sda.weather.info.repository;

import com.sda.weather.info.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM WEATHERINFO WHERE location_id = :locationId AND date = :date")
    WeatherInfo findByLocationIdAndDate(@Param("locationId") Long id, @Param("date") LocalDate now);
}
