package com.ev.charging.station.api.service;

import com.ev.charging.station.api.entity.Station;
import com.ev.charging.station.api.errors.StationNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StationService {
    List<Station> fetchAllStation() throws StationNotFoundException;

    ResponseEntity<Station> fetchStationById(Long stationId) throws StationNotFoundException;

    ResponseEntity<?> saveStation(MultipartFile file, String data) throws IOException;

    ResponseEntity<?> updateStationById(Long stationId, String modifiedData, MultipartFile file) throws StationNotFoundException, IOException;

    ResponseEntity<?> deleteStationById(Long stationId) throws StationNotFoundException;

    List<Station> fetchStationsLimited(int limit);

    List<Station> fetchStationsBySorted(String sort, String param);
}
