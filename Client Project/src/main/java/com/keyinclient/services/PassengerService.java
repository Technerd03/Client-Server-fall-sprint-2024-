package com.keyinclient.services;

import com.keyinclient.models.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerServiceTest {
    private PassengerService passengerService;
    private ApiService apiServiceMock;

    @BeforeEach
    void setUp() {
        apiServiceMock = Mockito.mock(ApiService.class);
        passengerService = new PassengerService();
        passengerService.setApiService(apiServiceMock);
    }

    @Test
    void testGetPassengerById() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/passengers/1"))
            .thenReturn("{ \"id\": 1, \"firstName\": \"John\" }");

        Passenger result = passengerService.getPassengerById(1L);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetAllPassengers() {
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/passengers"))
            .thenReturn("[{ \"id\": 1, \"firstName\": \"John\" }, { \"id\": 2, \"firstName\": \"Jane\" }]");

        List<Passenger> passengers = passengerService.getAllPassengers();
        assertEquals(2, passengers.size());
        assertEquals("John", passengers.get(0).getFirstName());
        assertEquals("Jane", passengers.get(1).getFirstName());
    }

    @Test
    void testCreatePassenger() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("NewPassenger");
        when(apiServiceMock.sendPostRequest("http://localhost:8080/api/passengers", "{\"firstName\":\"NewPassenger\"}"))
            .thenReturn("{ \"id\": 1, \"firstName\": \"NewPassenger\" }");

        Passenger createdPassenger = passengerService.createPassenger(passenger);
        assertEquals(1L, createdPassenger.getId());
        assertEquals("NewPassenger", createdPassenger.getFirstName());
    }

    @Test
    void testUpdatePassenger() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("UpdatedPassenger");
        when(apiServiceMock.sendPutRequest("http://localhost:8080/api/passengers/1", "{\"firstName\":\"UpdatedPassenger\"}"))
            .thenReturn("{ \"id\": 1, \"firstName\": \"UpdatedPassenger\" }");

        Passenger updatedPassenger = passengerService.updatePassenger(1L, passenger);
        assertEquals(1L, updatedPassenger.getId());
        assertEquals("UpdatedPassenger", updatedPassenger.getFirstName());
    }

    @Test
    void testDeletePassenger() {
        doNothing().when(apiServiceMock).sendDeleteRequest("http://localhost:8080/api/passengers/1");

        assertDoesNotThrow(() -> passengerService.deletePassenger(1L));
    }
}