package com.keyinclient.services;

import com.keyinclient.models.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirportServiceTest {
    private AirportService airportService;
    private ApiService apiServiceMock;

    @BeforeEach
    void setUp() {
        apiServiceMock = Mockito.mock(ApiService.class);
        airportService = new AirportService();
        airportService.setApiService(apiServiceMock);
    }

    @Test
    void testGetAirportById() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setName("MainAirport");
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/airports/1"))
            .thenReturn("{ \"id\": 1, \"name\": \"MainAirport\" }");

        Airport result = airportService.getAirportById(1L);
        assertEquals(1L, result.getId());
        assertEquals("MainAirport", result.getName());
    }

    @Test
    void testGetAllAirports() {
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/airports"))
            .thenReturn("[{ \"id\": 1, \"name\": \"Airport1\" }, { \"id\": 2, \"name\": \"Airport2\" }]");

        List<Airport> airports = airportService.getAllAirports();
        assertEquals(2, airports.size());
        assertEquals("Airport1", airports.get(0).getName());
        assertEquals("Airport2", airports.get(1).getName());
    }

    @Test
    void testCreateAirport() {
        Airport airport = new Airport();
        airport.setName("NewAirport");
        when(apiServiceMock.sendPostRequest("http://localhost:8080/api/airports", "{\"name\":\"NewAirport\"}"))
            .thenReturn("{ \"id\": 1, \"name\": \"NewAirport\" }");

        Airport createdAirport = airportService.createAirport(airport);
        assertEquals(1L, createdAirport.getId());
        assertEquals("NewAirport", createdAirport.getName());
    }

    @Test
    void testUpdateAirport() {
        Airport airport = new Airport();
        airport.setName("UpdatedAirport");
        when(apiServiceMock.sendPutRequest("http://localhost:8080/api/airports/1", "{\"name\":\"UpdatedAirport\"}"))
            .thenReturn("{ \"id\": 1, \"name\": \"UpdatedAirport\" }");

        Airport updatedAirport = airportService.updateAirport(1L, airport);
        assertEquals(1L, updatedAirport.getId());
        assertEquals("UpdatedAirport", updatedAirport.getName());
    }

    @Test
    void testDeleteAirport() {
        doNothing().when(apiServiceMock).sendDeleteRequest("http://localhost:8080/api/airports/1");

        assertDoesNotThrow(() -> airportService.deleteAirport(1L));
    }
}