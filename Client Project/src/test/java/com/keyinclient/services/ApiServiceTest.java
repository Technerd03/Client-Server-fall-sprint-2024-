package com.keyinclient.services;

import com.keyinclient.models.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {
    private CityService cityService;
    private ApiService apiServiceMock;

    @BeforeEach
    void setUp() {
        apiServiceMock = Mockito.mock(ApiService.class);
        cityService = new CityService();
        cityService.setApiService(apiServiceMock);
    }

    @Test
    void testGetCityById() {
        City city = new City();
        city.setId(1L);
        city.setName("TestCity");
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/cities/1")).thenReturn("{ \"id\": 1, \"name\": \"TestCity\" }");

        City result = cityService.getCityById(1L);
        assertEquals(1L, result.getId());
        assertEquals("TestCity", result.getName());
    }

    @Test
    void testGetAllCities() {
        when(apiServiceMock.sendGetRequest("http://localhost:8080/api/cities")).thenReturn("[{ \"id\": 1, \"name\": \"City1\" }, { \"id\": 2, \"name\": \"City2\" }]");

        List<City> cities = cityService.getAllCities();
        assertEquals(2, cities.size());
        assertEquals("City1", cities.get(0).getName());
        assertEquals("City2", cities.get(1).getName());
    }

    @Test
    void testCreateCity() {
        City city = new City();
        city.setName("NewCity");
        when(apiServiceMock.sendPostRequest("http://localhost:8080/api/cities", "{\"name\":\"NewCity\"}"))
            .thenReturn("{ \"id\": 1, \"name\": \"NewCity\" }");

        City createdCity = cityService.createCity(city);
        assertEquals(1L, createdCity.getId());
        assertEquals("NewCity", createdCity.getName());
    }

    @Test
    void testUpdateCity() {
        City city = new City();
        city.setName("UpdatedCity");
        when(apiServiceMock.sendPutRequest("http://localhost:8080/api/cities/1", "{\"name\":\"UpdatedCity\"}"))
            .thenReturn("{ \"id\": 1, \"name\": \"UpdatedCity\" }");

        City updatedCity = cityService.updateCity(1L, city);
        assertEquals(1L, updatedCity.getId());
        assertEquals("UpdatedCity", updatedCity.getName());
    }

    @Test
    void testDeleteCity() {
        doNothing().when(apiServiceMock).sendDeleteRequest("http://localhost:8080/api/cities/1");

        assertDoesNotThrow(() -> cityService.deleteCity(1L));
    }