package com.ev.charging.station.api.service;

import com.ev.charging.station.api.entity.Station;
import com.ev.charging.station.api.errors.StationNotFoundException;
import com.ev.charging.station.api.repository.StationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ObjectMapper mapper;


    @Override
    public List<Station> fetchAllStation() throws StationNotFoundException {

        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty())
            throw new StationNotFoundException("Stations Not Found");
        return stations;
    }

    @Override
    public ResponseEntity<Station> fetchStationById(Long stationId) throws StationNotFoundException {

        Station station = stationRepository.findById(stationId).orElseThrow(
                () -> new StationNotFoundException("Station not found for this id : " + stationId));
        return ResponseEntity.ok().body(station);
    }

    @Override
    public ResponseEntity<?> saveStation(MultipartFile file, String data) throws FileNotFoundException {

        Station station;
        if (!isJpegOrPng(file))
            throw new FileNotFoundException("File format is invalid, Valid{JPEG, PNG}");

        try {
            station = mapper.readValue(data, Station.class);
            station.setStationImage(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Json Format");
        }
        station = stationRepository.save(station);
        return ResponseEntity.status(HttpStatus.OK).body(station);
    }

    @Override
    public ResponseEntity<?> updateStationById(Long stationId, String modifiedData, MultipartFile file) throws StationNotFoundException, IOException {

        Station stationById = stationRepository.findById(stationId).orElseThrow(
                () -> new StationNotFoundException("Station not found for this id" + stationId));

        boolean empty = file.isEmpty();

        if (!empty)
            if (!isJpegOrPng(file))
                throw new FileNotFoundException("File format is invalid, Valid{JPEG, PNG}");

        Station station;
        station = mapper.readValue(modifiedData, Station.class);
        station.setStationId(stationId);
        if (!empty)
            station.setStationImage(file.getBytes());


        if (Objects.nonNull(station.getStationName()) && !"".equalsIgnoreCase(station.getStationName()))
            stationById.setStationName(station.getStationName());

        if (Objects.nonNull(station.getStationAddress()) && !"".equalsIgnoreCase(station.getStationAddress()))
            stationById.setStationAddress(station.getStationAddress());

        if (Objects.nonNull(station.getStationPricing()))
            stationById.setStationPricing(station.getStationPricing());

        if (!empty)
            stationById.setStationImage(station.getStationImage());

        stationById = stationRepository.save(stationById);
        return ResponseEntity.status(HttpStatus.OK).body(stationById);
    }

    @Override
    public ResponseEntity<?> deleteStationById(Long stationId) throws StationNotFoundException {

        Station stationById = stationRepository.findById(stationId).orElseThrow(
                () -> new StationNotFoundException("Station not found for this id " + stationId));
        stationRepository.deleteById(stationById.getStationId());
        return ResponseEntity.status(HttpStatus.OK).body("station id " + stationId + " deleted successfully.");
    }

    @Override
    public List<Station> fetchStationsLimited(int limit) {
        return stationRepository.findAll(PageRequest.of(0, limit)).getContent();
    }

    @Override
    public List<Station> fetchStationsBySorted(String sort, String param) {

        List<Station> stations = null;
        if (sort.equals("asc")) {

            if (param.equals("stationId"))
                stations = stationRepository.findByOrderByStationIdAsc();
            else if (param.equals("stationPricing"))
                stations = stationRepository.findByOrderByStationPricingAsc();
            else if (param.equals("stationName"))
                stations = stationRepository.findByOrderByStationNameAsc();
            else if (param.equals("stationId"))
                stations = stationRepository.findByOrderByStationIdAsc();
            else if (param.equals("stationAddress"))
                stations = stationRepository.findByOrderByStationAddressAsc();
        } else if (sort.equals("desc")) {

            if (param.equals("stationId"))
                stations = stationRepository.findByOrderByStationIdDesc();
            else if (param.equals("stationPricing"))
                stations = stationRepository.findByOrderByStationPricingDesc();
            else if (param.equals("stationName"))
                stations = stationRepository.findByOrderByStationNameDesc();
            else if (param.equals("stationId"))
                stations = stationRepository.findByOrderByStationIdDesc();
            else if (param.equals("stationAddress"))
                stations = stationRepository.findByOrderByStationAddressDesc();
        }
        return stations;
    }


    private boolean isJpegOrPng(MultipartFile file) {

        String contentType = file.getContentType();

        return contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/jpg");
    }
}
