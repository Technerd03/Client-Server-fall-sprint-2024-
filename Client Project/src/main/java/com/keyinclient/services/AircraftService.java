package com.keyinclient.services;

import com.keyinclient.models.Aircraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AircraftServiceTest {
    private AircraftService aircraftService;
    private ApiService apiServiceMock;

    @BeforeEach
    void setUp() {
        apiServiceMock = Mockito.mock(ApiService.class);
        aircraftService = new AircraftService();
        aircraftService.setApiService(apiServiceMock);
    }

    @Test
    void testGetAircraftById() {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setType("Boeing");
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/aircraft/1"))
            .thenReturn("{ \"id\": 1, \"type\": \"Boeing\" }");

        Aircraft result = aircraftService.getAircraftById(1L);
        assertEquals(1L, result.getId());
        assertEquals("Boeing", result.getType());
    }

    @Test
    void testGetAllAircraft() {
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/aircraft"))
            .thenReturn("[{ \"id\": 1, \"type\": \"Boeing\" }, { \"id\": 2, \"type\": \"Airbus\" }]");

        List<Aircraft> aircraft = aircraftService.getAllAircraft();
        assertEquals(2, aircraft.size());
        assertEquals("Boeing", aircraft.get(0).getType());
        assertEquals("Airbus", aircraft.get(1).getType());
    }

    @Test
    void testCreateAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("NewAircraft");
        when(apiServiceMock.sendPostRequest("http://localhost:8080/api/aircraft", "{\"type\":\"NewAircraft\"}"))
            .thenReturn("{ \"id\": 1, \"type\": \"NewAircraft\" }");

        Aircraft createdAircraft = aircraftService.createAircraft(aircraft);
        assertEquals(1L, createdAircraft.getId());
        assertEquals("NewAircraft", createdAircraft.getType());
    }

    @Test
    void testUpdateAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("UpdatedAircraft");
        when(apiServiceMock.sendPutRequest("http://localhost:8080/api/aircraft/1", "{\"type\":\"UpdatedAircraft\"}"))
            .thenReturn("{ \"id\": 1, \"type\": \"UpdatedAircraft\" }");

        Aircraft updatedAircraft = aircraftService.updateAircraft(1L, aircraft);
        assertEquals(1L, updatedAircraft.getId());
        assertEquals("UpdatedAircraft", updatedAircraft.getType());
    }

    @Test
    void testDeleteAircraft() {
        doNothing().when(apiServiceMock).sendDeleteRequest("http://localhost:8080/api/aircraft/1");

        assertDoesNotThrow(() -> aircraftService.deleteAircraft(1L));
    }
}