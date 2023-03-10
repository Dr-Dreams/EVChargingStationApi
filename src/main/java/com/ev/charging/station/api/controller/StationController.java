package com.ev.charging.station.api.controller;

import com.ev.charging.station.api.entity.Station;
import com.ev.charging.station.api.errors.StationNotFoundException;
import com.ev.charging.station.api.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/stations/")
public class StationController {

    private final Logger logger = LoggerFactory.getLogger(StationController.class);
    @Autowired
    private StationService stationService;

    /////////////////////////-----------Getting a list of all data --------/////////////////////////////////////////////

    @GetMapping()
    @Operation(summary = "Fetch all stations", description = "Get a list of all stations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Station.class))}),
            @ApiResponse(responseCode = "404", description = "Stations not found")
    })
    public List<Station> fetchAllStation() throws StationNotFoundException {
        logger.info("fetching all the data");
        return stationService.fetchAllStation();
    }

    /////////////////////////-----------Getting the station by Id --------/////////////////////////////////////////////////

    @GetMapping("/show/{id}")
    @Operation(summary = "Fetch a station by ID", description = "Get the details of a station by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Station.class))}),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<Station> fetchStationById(@PathVariable("id") Long stationId) throws StationNotFoundException {
        logger.info("fetching the data for specific id : {}", stationId);
        return stationService.fetchStationById(stationId);
    }

    /////////////////////////-----------Saving the station --------////////////////////////////////////////////////////////

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save a station", description = "Saves a new station with the given file and data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station saved successfully",
                    content = {@Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = Station.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> saveStation(@RequestParam("file") MultipartFile file, @RequestParam("data") @Parameter(description = "The modified data of the station", required = true,
            example = "{ \"stationName\": \"StationName\", \"stationPricing\": \"StationPricing\",\"stationAddress\": \"StationAddress\" }") String data) throws IOException {

        if (file.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file isn't present");
        logger.info("saving the data");
        return stationService.saveStation(file, data);
    }

    /////////////////////////-----------Updating the station --------///////////////////////////////////////////////////

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update a station by ID", description = "Updates an existing station with the given ID, modified data, and modified file.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station updated successfully",
                    content = {@Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = Station.class))}),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<?> updateStationById(@PathVariable("id") Long stationId, @RequestParam("data") @Parameter(description = "The modified data of the station", required = true,
            example = "{ \"stationName\": \"UpdatedStationName\", \"stationPricing\": \"UpdatedStationPrice\",\"stationAddress\": \"UpdatedStationAddress\" }") String modifiedData, @RequestParam(value = "file") MultipartFile file) throws StationNotFoundException, IOException {

        logger.info("updating the data for this id : {}", stationId);
        return stationService.updateStationById(stationId, modifiedData, file);
    }


    /////////////////////////-----------Deleting the station --------///////////////////////////////////////////////////


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a station by ID", description = "Deletes an existing station with the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Station not found")
    })
    public ResponseEntity<?> deleteStationById(@PathVariable("id") Long stationId) throws StationNotFoundException {
        logger.info("deleting the data for this id : {}", stationId);
        return stationService.deleteStationById(stationId);
    }

    ////////-----------Getting the list of stations sorted by a specified parameter and sorting order.--------//////////


    @GetMapping(params = {"sort", "param"}, headers = "methodType=sort")
    @Operation(summary = "Fetch stations by sorting", description = "Fetches a list of stations sorted by a specified parameter and sorting order."
            , operationId = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stations fetched and sorted successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Station.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid sorting parameter or order")
    })
    public List<Station> fetchStationsBySorted(@RequestParam("sort") @Parameter(example = "asc or desc") String sort, @RequestParam("param") @Parameter(example = "stationId or stationName or stationPricing or stationAddress") String param) {

        logger.info("sorting the data based on sort type and parameter type :", sort, param);
        return stationService.fetchStationsBySorted(sort, param);
    }


    /////////////////////////-----------Getting the list of station with limit --------/////////////////////////////////


    @GetMapping(params = "limit", headers = "methodType=limit")
    @Operation(summary = "Fetch stations with a limit", description = "Fetches a limited number of stations.",
            operationId = "2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stations fetched successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Station.class))})
    })
    public List<Station> fetchStationsLimited(@RequestParam("limit") int limit) {
        logger.info("fetching the data with limit variable : ", limit);
        return stationService.fetchStationsLimited(limit);
    }
}
