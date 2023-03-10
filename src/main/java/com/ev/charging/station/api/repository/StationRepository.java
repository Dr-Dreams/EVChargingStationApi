package com.ev.charging.station.api.repository;

import com.ev.charging.station.api.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findByOrderByStationIdAsc();

    List<Station> findByOrderByStationNameAsc();

    List<Station> findByOrderByStationPricingAsc();

    List<Station> findByOrderByStationAddressAsc();

    List<Station> findByOrderByStationIdDesc();

    List<Station> findByOrderByStationNameDesc();

    List<Station> findByOrderByStationPricingDesc();

    List<Station> findByOrderByStationAddressDesc();

}
